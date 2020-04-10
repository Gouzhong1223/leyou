package com.leyou.api;

import com.leyou.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-27 23:28
 * @email : 1162864960@qq.com
 */
public interface UserApi {
    @GetMapping("query")
    public User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password);
}
