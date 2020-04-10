package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 0:14
 * @email : 1162864960@qq.com
 */
public interface UploadService {
    String uploadImage(MultipartFile file);

    String upload(MultipartFile file);
}