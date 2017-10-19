package auto.qr.service.user;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.qr.dao.user.IUserDao;

@CommonsLog
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    
    private static final long USER_EXPIRE_TIME = 1000l * 60 * 60 * 24 * 7;
    
    private static final int PHONENUM_EXPIRE_TIME = 60 * 2 ;//短信验证码  过期时间  2分钟
    
    private static final int PICCODE_EXPIER_TIEM = 60*2 ;//图片验证码 过期时间 30秒

    @Autowired
    private IUserDao userDao;
    
    @Qualifier(value = "masterUserService")
    @Autowired
    private auto.master.service.user.IUserService userService;
}
