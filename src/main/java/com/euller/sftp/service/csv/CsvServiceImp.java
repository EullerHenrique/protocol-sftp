package com.euller.sftp.service.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvServiceImp implements CsvService{

    public void manipulateCSV(int pci, int pcf){

        try{

            //BuferedReader: Lê texto de um fluxo de entrada de caracteres, armazenando caracteres em buffer para
            //fornecer uma leitura eficiente de caracteres, matrizes e linhas.
            //O tamanho do buffer pode ser especificado ou o tamanho padrão pode ser usado. O padrão é grande o
            //suficiente para a maioria dos propósitos.
            //Em geral, cada solicitação de leitura feita de um Reader faz com que uma solicitação de leitura
            //correspondente seja feita do caractere ou fluxo de bytes subjacente. Portanto, é aconselhável envolver
            //um BufferedReader em qualquer Reader cujas operações read() possam ser caras, como FileReaders e
            //InputStreamReaders. Por exemplo,
            //  Leitor em buffer em
            //    = new BufferedReader(new FileReader("foo.in"));
            //
            //armazenará em buffer a entrada do arquivo especificado. Sem buffer, cada chamada de read() ou readLine()
            //poderia fazer com que os bytes fossem lidos do arquivo, convertidos em caracteres e então retornados,
            //o que pode ser muito ineficiente.
            //Programas que usam DataInputStreams para entrada textual podem ser localizados substituindo cada
            //DataInputStream por um BufferedReader apropriado.

            //InputStreamReader: Um InputStreamReader é uma ponte de fluxos de bytes para fluxos de caracteres:
            //ele lê bytes e os decodifica em caracteres usando um charset. O charset que ele usa pode ser especificado
            //pelo nome ou pode ser dado explicitamente, ou o charset padrão da plataforma pode ser aceito.
            //Cada chamada de um dos métodos read() de um InputStreamReader pode fazer com que um ou mais bytes sejam
            //lidos do fluxo de entrada de bytes subjacente. Para habilitar a conversão eficiente de bytes em caracteres,
            //mais bytes podem ser lidos antes do fluxo subjacente do que o necessário para satisfazer a operação de
            //leitura atual.
            //Para maior eficiência, considere agrupar um InputStreamReader em um BufferedReader. Por exemplo:
            //Leitor em buffer em
            //   = new BufferedReader(new InputStreamReader(System.in));


            //FileInputStream: Cria um FileInputStream abrindo uma conexão com um arquivo real, o arquivo nomeado pelo
            //nome do caminho no sistema de arquivos. Um novo objeto FileDescriptor é criado para representar essa
            //conexão de arquivo.
            //Primeiro, se houver um gerenciador de segurança, seu método checkRead é chamado com o argumento name
            //como argumento.
            //Se o arquivo nomeado não existir, for um diretório em vez de um arquivo normal ou, por algum outro motivo,
            //não puder ser aberto para leitura, uma FileNotFoundException será lançada.

            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    //ISO_8859_1: SO/IEC 8859-1 (informalmente, Latin1) é uma codificação de caracteres do alfabeto latino.
                                    //Inclui caracteres acentuados.
                                    //Este padrão é a base de outros mapeamentos amplamente usados como o ISO-8859-1 e o Windows-1252.
                                    //(CP-1252: Códificação padrão do excel)
                                    new FileInputStream(
                                            new File("src/main/resources/download/11/input/31-05-2022-0152359-clarobr.csv").getAbsolutePath()
                                    ),
                                    StandardCharsets.ISO_8859_1));

            //lines: Retorna um Stream, cujos elementos são linhas lidas deste BufferedReader. O Stream é preenchido
            //lentamente, ou seja, somente leitura ocorre durante a operação de fluxo do terminal.
            //O leitor não deve ser operado durante a execução da operação de fluxo do terminal. Caso contrário, o
            //resultado da operação de fluxo terminal é indefinido.
            //Após a execução da operação de fluxo do terminal, não há garantias de que o leitor estará em uma posição
            //específica para ler o próximo caractere ou linha.
            //Se uma IOException for lançada ao acessar o BufferedReader subjacente, ela será encapsulada em uma
            //UncheckedIOException que será lançada do método Stream que causou a leitura. Este método retornará um
            //Stream se invocado em um BufferedReader que está fechado. Qualquer operação nesse fluxo que exija a
            //leitura do BufferedReader depois que ele for fechado fará com que uma UncheckedIOException seja lançada.

            //collect: Executa uma operação de redução mutável nos elementos desse fluxo usando um Coletor.
            //Um Collector encapsula as funções usadas como argumentos para coletar (Supplier, BiConsumer, BiConsumer),
            //permitindo a reutilização de estratégias de coleta e composição de operações de coleta, como agrupamento
            //ou particionamento de vários níveis.
            //Se o fluxo for paralelo e o Coletor for concorrente e o fluxo não for ordenado ou o coletor não for
            //ordenado, uma redução simultânea será executada
            //Esta é uma operação terminal.
            //Quando executados em paralelo, vários resultados intermediários podem ser instanciados, preenchidos e
            //mesclados para manter o isolamento de estruturas de dados mutáveis. Portanto, mesmo quando executado em
            //paralelo com estruturas de dados não thread-safe (como ArrayList), nenhuma sincronização adicional é
            //necessária para uma redução paralela

            //joining: Retorna um Collector que concatena os elementos de entrada, separados pelo delimitador
            //especificado, na ordem de encontro.

            //System.lineSeparator: Retorna a string do separador de linha dependente do sistema.
            //Ele sempre retorna o mesmo valor - o valor inicial da propriedade do sistema line.separator.
            //Em sistemas UNIX, retorna "\n"; em sistemas Microsoft Windows, ele retorna "\r\n".

            // Transforma o csv com colunas divididas por ; em um csv com colunas dividas em , ( O OpenCSV só funciona com CSVS dividos com ,)
            String linesInput = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            linesInput = linesInput.replace(";", ",");

            //System.out.println(bufferedReader.lines());
            //CVSReader:
            //StringReader:
            //cvs.readAll:

            //Lê o csv
            CSVReader csvReader = new CSVReader(new StringReader(linesInput));
            List<String[]> linesOutput = csvReader.readAll();

            //File:
            //file.listFiles:

            //Verifica se todos os vínculos (mp3) do csv existem
            File file =  new File( new File("src/main/resources/download/11/input").getAbsolutePath());
            File[] files = file.listFiles();

            List<Boolean> bs = new ArrayList<>();
            for (int i = 0; i < linesOutput.size(); i++) {
                if (i > 0) {
                    int ii = i;
                    //anyMatch:
                    //equals:
                    //split:
                    if (Arrays.stream(files).anyMatch(f -> linesOutput.get(ii)[4].equals(f.getName().split("\\.")[0]))) {
                        bs.add(true);
                    } else {
                        bs.add(false);
                    }
                }
            }

            //allMatch:
            Boolean vd = bs.stream().allMatch(b -> b == true);

            //Troca uma coluna de lugar com outra se todos os vínculos (mp3) do csv existiren

            if(vd) {

                for (String[] columns : linesOutput) {
                    {

                        String temp = columns[pci];

                        columns[pci] = columns[pcf];
                        columns[pcf] = temp;

                    }
                }

                //
                csvReader.close();

                //
                FileOutputStream fileOutputStream = new FileOutputStream(new File("src/main/resources/download/11/output/31-05-2022-0152359-claro-v2.csv").getAbsolutePath());

                //
                //
                //
                //
                //

                //ISO_8859_1: (informalmente, Latin1) é uma codificação de caracteres do alfabeto latino.
                //Inclui caracteres acentuados.
                //Este padrão é a base de outros mapeamentos amplamente usados como o ISO-8859-1 e o Windows-1252.
                //(CP-1252: Códificação padrão do excel)

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.ISO_8859_1);
                CSVWriter csvWriter = new CSVWriter(outputStreamWriter, ';', '"', '"', "\n");
                csvWriter.writeAll(linesOutput);
                csvWriter.flush();
                csvWriter.close();

            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (CsvException e){
            e.printStackTrace();
        }
    }
}
