package auto.common.service.message;

public interface Message {
    
    public enum Priority {
        LOW, NORMAL, HIGH,
    }
    
    Priority getPriority();
    
}
