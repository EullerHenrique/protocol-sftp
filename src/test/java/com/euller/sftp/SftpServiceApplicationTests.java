package com.euller.sftp;

import com.euller.sftp.service.SftpService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SftpServiceApplicationTests {

    @Test
    public void download() {
        String download = new SftpService().download();
        System.out.println(download);
    }

}
