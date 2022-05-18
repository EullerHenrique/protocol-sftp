package com.euller.sftp.service;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SftpService {

        private DefaultSftpSessionFactory getFactory() {
            DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(22);
            factory.setUser("euller");
            factory.setPassword("12345");
            factory.setAllowUnknownKeys(true);
            return factory;
        }


        public void upload(InputStream inputStream, String filePath, String fileName) throws IOException {
            try (SftpSession session = getFactory().getSession()) {
                session.write(inputStream,filePath + "/" + fileName);
            }
        }

    public String download() {
        SftpSession session = getFactory().getSession();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            session.read("upload/Users.csv", outputStream);
            return outputStream.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

