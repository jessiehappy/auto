/**
 * 
 */
package auto.common.service.mq;

import auto.datamodel.IWritable;

public interface IMessageQueueManager {
    
    public boolean sendMessage(String topic, String key, IWritable value);
    
    public boolean registerListener(String topic, IMessageListener listener);

}
