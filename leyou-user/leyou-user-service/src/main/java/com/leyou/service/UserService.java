package com.leyou.service;

import com.leyou.pojo.User;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-24 17:46
 * @email : 1162864960@qq.com
 */
public interface UserService {
    Boolean checkUser(String data, Integer type);

    void sendVerifyCode(String phone);

    User queryUser(String username, String password);

    Boolean register(User user, String code);
}
