package com.lilith.galamall.service.impl;

import com.google.common.collect.Lists;
import com.lilith.galamall.service.FileService;
import com.lilith.galamall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author:JiaJingnan
 * @Date: 上午8:48 2021/6/12
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 对上传的文件重命名，放置上传同名文件时被覆盖
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件,上传的文件名是" + fileName + ",上传的路径是" + path + ",上传后的新文件名是" + uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            // 不存在时创建文件夹
            fileDir.setWritable(true);
            // 创建层级目录
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件已经上传成功

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 删除文件
            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
