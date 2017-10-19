/**
 * 
 */
package auto.common.service.mq;

import auto.datamodel.IWritable;

public interface IMessageListener {
    
    public Class<? extends IWritable> getValueClazz();
    
    public void onMessageReceived(String topic, String key, IWritable value);

}
