package auto.datamodel.cache;

import auto.datamodel.dao.CustomUser;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.ProxyUser;

public enum CacheType {
    
    id2CustomUser, username2CustomUser, customeToken2Id, //custome
    id2ProxyUser, username2ProxyUser, proxyToken2Id, //proxy
    id2dealerUser, username2DealerUser, dealerToken2Id, //dealer
    ;
    
    @SuppressWarnings("unchecked")
    public static final Class<? extends ICacheable>[] clazzes = new Class[]{
    		CustomUser.class, PrimitiveCacheable.class, PrimitiveCacheable.class,
    		ProxyUser.class, PrimitiveCacheable.class, PrimitiveCacheable.class,
    		DealerUser.class, PrimitiveCacheable.class, PrimitiveCacheable.class,
    };
    
}
