package auto.datamodel.cache;


public enum CacheType {
    
    id2User, username2Id, token2Id, //user
    ;
    
    @SuppressWarnings("unchecked")
    public static final Class<? extends ICacheable>[] clazzes = new Class[]{
        
    };
    
}
