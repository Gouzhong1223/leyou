package com.leyou.client;

import com.leyou.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-27 23:30
 * @email : 1162864960@qq.com
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
