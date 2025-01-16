package com.scoder.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Shawn Cui
 */
public interface ISysFileService {

    public String uploadFile(MultipartFile file) throws Exception;
}
