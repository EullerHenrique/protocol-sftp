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


            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    //ISO_8859_1: SO/IEC 8859-1 (informalmente, Latin1) é uma codificação de caracteres do alfabeto latino.
                                    //Inclui caracteres acentuados.
                                    //Este padrão é a base de outros mapeamentos amplamente usados como o ISO-8859-1 e o Windows-1252.
                                    //(CP-1252: Códificação padrão do excel)
                                    new FileInputStream(
                                            "D:/Documents/Study/UFU/CURSOS/SFTP/sftp/src/main/resources/download/11/input/31-05-2022-0152359-clarobr.csv"), StandardCharsets.ISO_8859_1));

            // Transforma o csv com colunas divididas por ; em um csv com colunas dividas em , ( O OpenCSV só funciona com CSVS dividos com ,)
            String linesInput = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            linesInput = linesInput.replace(";", ",");


            //Lê o csv
            CSVReader csvReader = new CSVReader(new StringReader(linesInput));
            List<String[]> linesOutput = csvReader.readAll();


            //Verifica se todos os vínculos (mp3) do csv existem

            File file = new File("D:/Documents/Study/UFU/CURSOS/SFTP/sftp/src/main/resources/download/11/input/");
            File[] files = file.listFiles();

            List<Boolean> bs = new ArrayList<>();
            for (int i = 0; i < linesOutput.size(); i++) {
                if (i > 0) {
                    int ii = i;
                    if (Arrays.stream(files).anyMatch(f -> linesOutput.get(ii)[4].equals(f.getName().split("\\.")[0]))) {
                        bs.add(true);
                    } else {
                        bs.add(false);
                    }
                }
            }

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

                csvReader.close();

                FileOutputStream fileOutputStream = new FileOutputStream("D:/Documents/Study/UFU/CURSOS/SFTP/sftp/src/main/resources/download/11/output/31-05-2022-0152359-clarobr2.csv");

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
