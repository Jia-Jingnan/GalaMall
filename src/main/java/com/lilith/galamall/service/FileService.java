package com.lilith.galamall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:JiaJingnan
 * @Date: 上午8:47 2021/6/12
 */
public interface FileService {

    String upload(MultipartFile file, String path);
}
