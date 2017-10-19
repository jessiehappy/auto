package auto.common.service.message;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MessageHandler {

    @Autowired
    protected MessageService messageService;

    /**
     * Register itself by {@link #getSubscription()}.
     */
    @PostConstruct
    public void register() {
        Class<? extends Message> message = getSubscription();
        Validate.notNull(message, "subscribe nothing: handler=" + this);
        messageService.register(message, this);
    }

    /**
     * @return subscribed types of messages.
     */
    abstract protected Class<? extends Message> getSubscription();

    /**
     * @param message
     *            assert non-null
     * @throws Exception
     *             may be retried when exception
     */
    abstract protected void handle(Message message) throws Exception;

}
