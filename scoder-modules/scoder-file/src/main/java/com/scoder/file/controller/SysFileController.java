package com.scoder.file.controller;

import com.scoder.common.core.domain.R;
import com.scoder.common.core.utils.file.FileUtils;
import com.scoder.file.api.domain.SysFile;
import com.scoder.file.service.ISysFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * SysFile Controller
 *
 * @author Shawn Cui
 */
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file) {
        try {
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("upload file failed", e);
            return R.fail(e.getMessage());
        }
    }
}
