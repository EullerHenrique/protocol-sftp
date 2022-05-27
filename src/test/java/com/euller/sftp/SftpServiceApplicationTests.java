package com.euller.sftp;

import com.euller.sftp.controller.SftpController;
import com.euller.sftp.service.sftp.SftpServiceImp;
import com.euller.sftp.service.thread.ThreadService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class SftpServiceApplicationTests {

    @Test
    public void download(){

        SftpController controller = new SftpController(new SftpServiceImp(new ThreadService()));

        LocalDateTime inicio = LocalDateTime.now();
        System.out.println("Início: "+inicio);

        for(int i=0; i < 10; i++){
            System.out.println(controller.download(i+1+""));
        }

        LocalDateTime fim = LocalDateTime.now();
        System.out.println("Fim: "+fim);

        double millis = inicio.until(fim, ChronoUnit.MILLIS);
        double seconds = millis/1000;

        System.out.println("Tempo decorrido: "+seconds + " segundos");

    }

}
