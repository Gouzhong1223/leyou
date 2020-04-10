package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-24 17:47
 * @email : 1162864960@qq.com
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 校验参数
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data,@PathVariable("type") Integer type){
        Boolean bool = this.userService.checkUser(data,type);
        if (bool ==null) {
            LOGGER.info("参数不合法 : {} {}",data,type);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);
    }

    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(User user,@RequestParam("code")String code){
        Boolean bool = this.userService.register(user,code);
        if (bool) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username,@RequestParam("password")String password){

        User user = this.userService.queryUser(username,password);

        if (user == null) {
            LOGGER.info("没有查询到 ： {}",username);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);

    }
}
