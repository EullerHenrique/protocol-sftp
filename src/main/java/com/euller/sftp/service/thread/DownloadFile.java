package com.euller.sftp.service.thread;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

//Callable: Uma task que retorna um resultado e pode lançar uma exceção.
//Os implementadores definem um único método sem argumentos chamado call.
//A interface Callable é semelhante à Runnable, pois ambas são projetadas para classes cujas instâncias são
//potencialmente executadas por outro thread.
//Um Runnable, no entanto, não retorna um resultado e não pode lançar uma exceção verificada.
//A classe Executors contém métodos utilitários para converter de outros formulários comuns em classes Callable.
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

        //session.read: Lê um arquivo e o armazena
        //Origem: upload/Users.csv Destino: outputStream
        sftpSession.read(this.source, this.outputStream);

        //ByteArrayResource: Implementação da interface Resource para uma determinada matriz de bytes.
        //Cria um ByteArrayInputStream para a matriz de bytes fornecida.

        //Resource: Interface para um descritor de recurso que abstrai do tipo real de recurso subjacente,
        //como um arquivo ou recurso de caminho de classe.
        //Um InputStream pode ser aberto para cada recurso se existir em formato físico, mas um URL ou identificador de
        //arquivo pode ser retornado apenas para determinados recursos.O comportamento real é específico da implementação.

        //ByteArrayInputStream: Um ByteArrayInputStream contém um buffer interno que contém bytes que podem ser lidos
        //do fluxo. Um contador interno acompanha o próximo byte a ser fornecido pelo método read.
        //Fechar um ByteArrayInputStream não tem efeito. Os métodos dessa classe podem ser chamados após o fechamento
        //do fluxo sem gerar uma IOException.

        //toByteArray: Cria uma matriz de bytes recém-alocada. Seu tamanho é o tamanho atual desse fluxo de saída e
        //o conteúdo válido do buffer foi copiado para ele.

        return new ByteArrayResource(this.outputStream.toByteArray());

    }

    //Calcula um resultado ou lança uma exceção se não for possível.
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
