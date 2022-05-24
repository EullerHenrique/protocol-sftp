package com.euller.sftp.service.thread;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class DownloadFile implements Callable<ByteArrayResource> {

    private final SftpSession sftpSession;
    private final String source;
    private final ByteArrayOutputStream outputStream;

    public DownloadFile(SftpSession sftpSession, String source, ByteArrayOutputStream outputStream){
        this.sftpSession = sftpSession;
        this.source = source;
        this.outputStream = outputStream;
    }

    public ByteArrayResource download() throws IOException{

        sftpSession.read(this.source, outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public ByteArrayResource call()  {
        try{
            return this.download();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
