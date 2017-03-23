package com.zkxy.xmoa.login;



import com.zkxy.xmoa.system.model.User;
import org.springframework.stereotype.Service;



@Service
public class LoginServiceImpl implements ILoginService{

//    @Resource
//    private UserMapper userMapper;

    @Override
    public boolean validateUser(User paramUser) {
        return  true ;
    }
}
