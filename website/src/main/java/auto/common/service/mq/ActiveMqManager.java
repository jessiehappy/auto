package auto.common.service.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import auto.datamodel.IWritable;
import auto.util.SerializeUtils;

@CommonsLog
@Component
public class ActiveMqManager implements IMessageQueueManager, MessageListener {
    
    private static final String USERNAME = "auto";
    
    private static final String PASSWORD = "autopassword";
    
    private static final String BROKER_URL = "tcp://active-mq-server:61616";
    
    private static final String TOPIC = "auto_topic";
    
    private ConnectionFactory factory;
    
    private Connection connection;
    
    private Session session;
    
    private MessageProducer producer;
    
    private MessageConsumer consumer;
    
    private Destination destination;
    
    private boolean needInit = true;
    
    private Map<String, List<IMessageListener>> topic2Listeners = new HashMap<String, List<IMessageListener>>();
    
    @PostConstruct
    public boolean init() {
        try {
            factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(TOPIC);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
            needInit = false;
            return true;
        } catch (Exception e) {
            log.error("connect active mq failed. " + e);
            close();
        }
        return false;
    }
    
    public void close() {
        try {
            if (producer != null) {
                producer.close();
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        try {
            if (consumer != null) {
                consumer.close();
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        producer = null;
        session = null;
        connection = null;
        needInit = true;
    }

    @Override
    public synchronized boolean sendMessage(String topic, String key, IWritable value) {
        if (needInit) {
            if (!init()) {
                return false;
            }
        }
        try {
            log.info("sendMessage start : topic = " + topic + ", key = " + key);
            MapMessage msg = session.createMapMessage();
            msg.setString("topic", topic);
            msg.setString("key", key);
            msg.setBytes("value", SerializeUtils.serialize(value));
            producer.send(destination, msg);
            log.info("sendMessage finished : topic = " + topic + ", key = " + key);
            return true;
        } catch(Exception e) {
            log.error(e, e);
            close();
        }
        return false;
    }

    @Override
    public boolean registerListener(String topic, IMessageListener listener) {
        synchronized(topic2Listeners) {
            List<IMessageListener> listeners = topic2Listeners.get(topic);
            if (listeners == null) {
                listeners = new ArrayList<IMessageListener>();
                topic2Listeners.put(topic, listeners);
            }
            listeners.add(listener);
        }
        return true;
    }

    @Override
    public void onMessage(Message msg) {
        synchronized (topic2Listeners) {
            try {
                MapMessage message = (MapMessage)msg;
                String topic = message.getString("topic");
                String key = message.getString("key");
                byte[] content = message.getBytes("value");
                List<IMessageListener> listeners = topic2Listeners.get(topic);
                if (listeners != null) {
                    for (IMessageListener listener : listeners) {
                        listener.onMessageReceived(
                                topic, key, SerializeUtils.deseialize(content, listener.getValueClazz()));
                    }
                }
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

}
