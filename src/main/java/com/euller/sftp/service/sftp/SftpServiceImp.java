package com.euller.sftp.service.sftp;

import com.euller.sftp.service.thread.ThreadService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

@Service
public class SftpServiceImp implements SftpService{

    //DefaultSftpSessionFactory: Fábrica de sessão Sftp padrão
    private final DefaultSftpSessionFactory sftpSessionFactory;
    private final ThreadService t;

    public SftpServiceImp(ThreadService t) {
        this.t = t;

        sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost("127.0.0.1");
        sftpSessionFactory.setPort(22);
        sftpSessionFactory.setUser("euller");
        sftpSessionFactory.setPassword("12345");
        //AllowUnknownKeys: Permitir chaves desconhecidas
        sftpSessionFactory.setAllowUnknownKeys(true);

    }


    public ByteArrayResource download(String file) {

        //SftpSession: Implementação padrão de SFTP Session. Encapsula uma instância de sessão JSCH.

        //JSCH:
        //O Java Secure Channel, ou JSch, foi desenvolvido pela empresa japonesa JCraft
        //com o intuito de permitir que usuários pudessem desfrutar de sessões seguras
        //em suas transferências de arquivos. Desta forma, a JCraft decidiu criar um
        //framework baseando-se nos mecanismos de segurança do protocolo SSH2,
        //tornando as sessões criptografadas e, portanto, seguras.

        //SSH:
        //SSH é uma sigla, ou acrônimo, para o termo secure shell, que significa cápsula segura.
        //Na prática, o protocolo SSH é um mecanismo de segurança oferecido pelos serviços de hospedagem.
        //A função dele é garantir que haja uma conexão segura entre o computador e o servidor remoto,
        //o que garante a transferência de dados sem nenhuma perda de informação
        //O SSH tem a função de permitir aos usuários e desenvolvedores realizarem qualquer modificação em sites
        //e servidores utilizando uma conexão simples. Dessa forma, por meio de um computador ligado à internet,
        //essa pessoa consegue configurar, modificar arquivos ou até mesmo trabalhar no desenvolvimento de uma página
        //da web.
        //A proposta desse protocolo é justamente criar um método seguro e que garanta que não haverá nenhuma invasão
        //desses arquivos e de seus códigos. Por isso, são usadas criptografias que garantem que somente dois pontos
        //acessem as informações: o servidor e o computador que enviou os dados para esse local remoto.

        //SSH2: SSH2 foi introduzido em 2006 com muitas melhorias significativas em relação ao SSH1.
        //Embora seja um aprimoramento do SSH1, o SSH2 não é compatível com o SSH1. SSH2 foi reescrito
        //com a adição de mais mecanismos defensivos para evitar vulnerabilidades.
        //O SSH2 usa um conjunto diferente de algoritmos aprimorados e mais fortes para criptografia e autenticação,
        //como DSA (Algoritmo de Assinatura Digital).

        SftpSession sftpSession = sftpSessionFactory.getSession();

        //ByteArrayOutputStream: Essa classe implementa um fluxo de saída no qual os dados são gravados em uma
        //matriz de bytes. O buffer cresce automaticamente à medida que os dados são gravados nele. Os dados podem
        //ser recuperados usando toByteArray() e toString().
        //Fechar um ByteArrayOutputStream não tem efeito. Os métodos nesta classe podem ser chamados após o fechamento
        //do fluxo sem gerar uma IOException
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            //session.read: Lê um arquivo e o armazena
            //Origem: upload/Users.csv Destino: outputStream
            //session.read("upload/Users.csv", outputStream);
            //return outputStream.toString();
            return (ByteArrayResource) t.create(sftpSession, "upload/"+file, outputStream).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
