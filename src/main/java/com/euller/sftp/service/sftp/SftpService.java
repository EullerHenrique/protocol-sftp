package com.euller.sftp.service.sftp;

import org.springframework.core.io.ByteArrayResource;

public interface SftpService {

    ByteArrayResource download(String file);

}

