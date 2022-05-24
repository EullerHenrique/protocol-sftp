package com.euller.sftp.service.thread;

import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ThreadService {

    private final ExecutorService es = Executors.newFixedThreadPool(10);
    public Future<?> create(SftpSession sftpSession, String source, ByteArrayOutputStream outputStream) {
        return es.submit(new DownloadFile(sftpSession, source, outputStream));
    }

}
