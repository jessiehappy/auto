package auto.master.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.master.dao.user.IUserDao;

@Service("masterUserService")
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private IUserDao userDao;

}
