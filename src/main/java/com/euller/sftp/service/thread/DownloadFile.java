package com.euller.sftp.service.thread;

import org.apache.commons.io.FileUtils;
import org.springframework.core.NestedIOException;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.*;
import java.util.concurrent.Callable;

//Callable: Uma task que retorna um resultado e pode lançar uma exceção.
//Os implementadores definem um único método sem argumentos chamado call.
//A interface Callable é semelhante à Runnable, pois ambas são projetadas para classes cujas instâncias são
//potencialmente executadas por outro thread.
//Um Runnable, no entanto, não retorna um resultado e não pode lançar uma exceção verificada.
//A classe Executors contém métodos utilitários para converter de outros formulários comuns em classes Callable.
public class DownloadFile implements Callable<Boolean> {

    private final SftpSession sftpSession;

    private final String file;

    //ByteArrayOutputStream: Essa classe implementa um fluxo de saída no qual os dados são gravados em uma
    //matriz de bytes. O buffer cresce automaticamente à medida que os dados são gravados nele. Os dados podem
    //ser recuperados usando toByteArray() e toString().
    //Fechar um ByteArrayOutputStream não tem efeito. Os métodos nesta classe podem ser chamados após o fechamento
    //do fluxo sem gerar uma IOException

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    public DownloadFile(SftpSession sftpSession, String file){
        this.sftpSession = sftpSession;
        this.file = file;
    }

    public Boolean download() throws IOException{

        //session.read: Lê um arquivo e o armazena
        //Origem: upload/Users.csv Destino: outputStream
        sftpSession.read("upload/"+this.file, outputStream);

        //FileOutputStream:

        //FileUtils.openOutputStream:

        String[] s1 = this.file.split("/");
        String[] s2 = s1[1].split("\\.");

        FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File("src//main//resources//download/" + s2[0]+s1[0]+"."+s2[1]));

        //fileOutputStream.write:

        //toByteArray: Cria uma matriz de bytes recém-alocada. Seu tamanho é o tamanho atual desse fluxo de saída e
        //o conteúdo válido do buffer foi copiado para ele.

        fileOutputStream.write(outputStream.toByteArray());

        return true;

    }

    //Calcula um resultado ou lança uma exceção se não for possível.
    @Override
    public Boolean call()  {

        try{
            return this.download();
        }
        catch(IOException e){
            return false;
        }

    }

}
