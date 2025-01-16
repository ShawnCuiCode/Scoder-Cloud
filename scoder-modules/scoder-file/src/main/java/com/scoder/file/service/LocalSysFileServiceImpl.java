package com.scoder.file.service;

import com.scoder.file.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Shawn Cui
 */
@Primary
@Service
public class LocalSysFileServiceImpl implements ISysFileService {

    @Value("${file.prefix}")
    public String localFilePrefix;

    @Value("${file.domain}")
    public String domain;

    @Value("${file.path}")
    private String localFilePath;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String name = FileUploadUtils.upload(localFilePath, file);
        String url = domain + localFilePrefix + name;
        return url;
    }
}
