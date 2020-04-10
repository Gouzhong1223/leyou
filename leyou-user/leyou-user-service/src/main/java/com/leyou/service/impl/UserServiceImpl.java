package com.leyou.service.impl;

import com.leyou.common.utils.NumberUtils;
import com.leyou.mapper.UserMapper;
import com.leyou.pojo.User;
import com.leyou.service.UserService;
import com.leyou.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-24 17:46
 * @email : 1162864960@qq.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String KEY_PREFIX = "user:verify:";

    @Override
    public Boolean checkUser(String data, Integer type) {

        User record = new User();
        if (type == 1) {
            record.setUsername(data);
        } else if (type == 2) {
            record.setPhone(data);
        } else {
            return null;
        }

        return this.userMapper.selectCount(record) == 0;
    }

    @Override
    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)) {
            LOGGER.info("电话号码为空，不能发送短信 : {}", phone);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        String code = NumberUtils.generateCode(6);
        map.put("code", code);
        this.amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "verify.code", map);

        this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);

    }

    @Override
    public User queryUser(String username, String password) {

        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }
        // 用户名密码都正确
        return user;

    }

    @Override
    public Boolean register(@Valid User user, String code) {

        String cachecode = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());

        if (!StringUtils.equals(code, cachecode)) {
            return false;
        }
        String sal = CodecUtils.generateSalt();
        user.setSalt(sal);

        user.setPassword(CodecUtils.md5Hex(user.getPassword(), sal));

        user.setId(null);
        user.setCreated(new Date());

        boolean b = this.userMapper.insertSelective(user) == 1;

        if (b) {
            this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return b;
    }
}
