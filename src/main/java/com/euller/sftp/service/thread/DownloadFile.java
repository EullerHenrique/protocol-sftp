package com.euller.sftp.service.thread;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.io.FileUtils;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.*;
import java.util.List;
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

    public DownloadFile(String file){

        //DefaultSftpSessionFactory: Fábrica de sessão Sftp padrão
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();

        //Local

        //sftpSessionFactory.setHost("127.0.0.1");
        //sftpSessionFactory.setPort(22);
        //sftpSessionFactory.setUser("euller");
        //sftpSessionFactory.setPassword("12345");

        //External

        sftpSessionFactory.setHost("10.120.11.106");
        sftpSessionFactory.setPort(2222);
        sftpSessionFactory.setUser("usermirabr");
        sftpSessionFactory.setPassword("a.123456");

        //AllowUnknownKeys: Permitir chaves desconhecidas
        sftpSessionFactory.setAllowUnknownKeys(true);

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

        this.sftpSession = sftpSessionFactory.getSession();
        this.file = file;

    }

    public Boolean download() throws IOException {

        System.out.println("Init "+this.file);

        //session.read: Lê um arquivo e o armazena
        //Origem: upload/Users.csv Destino: outputStream
        //sftpSession.read("upload/"+this.file, outputStream);

        List<ChannelSftp.LsEntry> list;
        try {
            list = List.of(sftpSession.list("upload/"+this.file));
        } catch (IOException e) {
            throw new IOException(e);
        }

        for(ChannelSftp.LsEntry entry: list){

            try {

                //ByteArrayOutputStream: Essa classe implementa um fluxo de saída no qual os dados são gravados em uma
                //matriz de bytes. O buffer cresce automaticamente à medida que os dados são gravados nele. Os dados podem
                //ser recuperados usando toByteArray() e toString().
                //Fechar um ByteArrayOutputStream não tem efeito. Os métodos nesta classe podem ser chamados após o fechamento
                //do fluxo sem gerar uma IOException

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                sftpSession.read("upload/"+this.file+"/"+entry.getFilename(), outputStream);

                //FileOutputStream: Um fluxo de saída de arquivo é um fluxo de saída para gravar dados em um arquivo ou em
                //um FileDescriptor, se um arquivo está disponível ou pode ser criado dependendo da plataforma subjacente.
                //Algumas plataformas, em particular, permitem que um arquivo seja aberto para gravação por apenas um
                //FileOutputStream (ou outro objeto de gravação de arquivo) por vez. Em tais situações, os construtores dessa
                //classe falharão se o arquivo envolvido já estiver aberto.
                //FileOutputStream destina-se a gravar fluxos de bytes brutos, como dados de imagem. Para escrever fluxos de
                //caracteres, considere usar FileWriter.

                //FileUtils.openOutputStream: Abre um FileOutputStream para o arquivo especificado, verificando e criando
                //o diretório pai se ele não existir.
                //No final do método, o fluxo será aberto com sucesso ou uma exceção será lançada.
                //O diretório pai será criado se não existir. O arquivo será criado se não existir. Uma exceção é lançada se
                //o objeto de arquivo existir, mas for um diretório. Uma exceção é lançada se o arquivo existir, mas não puder
                //ser gravado. Uma exceção é lançada se o diretório pai não puder ser criado.

                FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File("src/main/resources/download/" + this.file + "/" + entry.getFilename()));

                //fileOutputStream.write: Grava bytes b.length da matriz de bytes especificada neste fluxo de saída de arquivo.

                //toByteArray: Cria uma matriz de bytes recém-alocada. Seu tamanho é o tamanho atual desse fluxo de saída e
                //o conteúdo válido do buffer foi copiado para ele.

                fileOutputStream.write(outputStream.toByteArray());

                System.out.println("src/main/resources/download/" + this.file + "/"+ entry.getFilename() + " salvo");

            }catch (IOException ignored){
            }
        }

        return true;

    }

    @Override
    public Boolean call() {
        try {
            return this.download();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
