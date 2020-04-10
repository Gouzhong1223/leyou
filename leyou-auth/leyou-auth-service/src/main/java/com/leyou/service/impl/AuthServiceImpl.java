package com.leyou.service.impl;

import com.leyou.client.UserClient;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.User;
import com.leyou.pojo.UserInfo;
import com.leyou.service.AuthService;
import com.leyou.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-27 22:39
 * @email : 1162864960@qq.com
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String accredit(String username, String password) {
        try {
            // 调用微服务，执行查询
            User user = this.userClient.queryUser(username, password);

            // 如果查询结果为null，则直接返回null
            if (user == null) {
                return null;
            }

            // 如果有查询结果，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
