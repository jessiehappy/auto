package auto.common.service.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import auto.common.service.message.Message.Priority;
import auto.util.EmailUtils;
import auto.util.JsonUtils;

/**
 * Singleton.
 * 
 */
@Service
@CommonsLog
public class MessageService {

    private Map<Class<? extends Message>, Collection<MessageHandler>> handlerMap =
            new HashMap<Class<? extends Message>, Collection<MessageHandler>>();
    
    private Map<Integer, BlockingQueue<Message>> priority2Queues =
            new HashMap<Integer, BlockingQueue<Message>>();
    
    private static final int LOG_QUEUE_SIZE = 100;
    
    private static final int DISCARD_QUEUE_SIZE = 10000;

    public MessageService() {
        for (Priority priority : Priority.values()) {
            BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
            int num = priority.ordinal();
            priority2Queues.put(num, queue);
            for (int i = 0; i <= num * 2; i++) {
                MessageScheduler scheduler = new MessageScheduler(
                        "MessageServiceThread[" + num + "]-thread" + i, queue);
                scheduler.start();
            }
        }
    }

    /**
     * Make specified handler subscribe the given type of message.
     */
    public synchronized <T extends Message> void register(
            Class<T> messageClass, MessageHandler handler) {
        if (messageClass == null || handler == null) {
            return;
        }
        Collection<MessageHandler> collection = handlerMap.get(messageClass);
        if (collection == null) {
            collection = new ArrayList<MessageHandler>();
            handlerMap.put(messageClass, collection);
        }
        log.info("Register message handler: message="
                + messageClass.getSimpleName() + ", handler="
                + handler.getClass().getSimpleName());
        collection.add(handler);
    }

    /**
     * Thread-safe.
     */
    public void send(Message message) {
        if (message == null) {
            log.warn("Null message");
            return;
        }
        int priority = message.getPriority().ordinal();
        BlockingQueue<Message> messageQueue = priority2Queues.get(priority);
        messageQueue.offer(message);
        int size = messageQueue.size();
        if (size > LOG_QUEUE_SIZE) {
            log.info("message queue size is out of limit, size = " + size + ", priority = " + priority);
        }
        if (size > DISCARD_QUEUE_SIZE && priority == Priority.LOW.ordinal()) {
            messageQueue.clear();
            log.error("discard all data in low queue, size = " + size + ", queue = " + JsonUtils.toJson(messageQueue));
            EmailUtils.sendSystemMail(
                    "Low message queue overflowed!", "size = " + size + ", timestamp = " + System.currentTimeMillis());
        }
    }

    /**
     * Handle {@link MessageService#messageQueue}.
     * 
     * @author yanghao
     */
    private class MessageScheduler extends Thread {
        
        private BlockingQueue<Message> messageQueue;
        
        public MessageScheduler(String name, BlockingQueue<Message> messageQueue) {
            super(name);
            this.messageQueue = messageQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    schedule(messageQueue.take());
                } catch (Exception e) {
                    log.error("Failed to take message from queue", e);
                }
            }
        }

        private void schedule(Message message) {
            if (message == null) {
                return;
            }
            Collection<MessageHandler> handlers = handlerMap.get(message.getClass());
            if (CollectionUtils.isEmpty(handlers)) {
                log.warn("No handler for message: " + message);
                return;
            }
            for (MessageHandler handler : handlers) {
                try {
                    handler.handle(message);
                } catch (Exception e) {
                    log.error("Handle message failed: " + message, e);
                }
            }
        }

    }

}
