package com.euller.sftp.service.sftp;

import com.euller.sftp.service.thread.ThreadService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
@AllArgsConstructor
public class SftpServiceImp implements SftpService{
    private final ThreadService t;

    public Boolean download(String file)  {
        try {
            return (Boolean) t.create(file).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void manipulateCSV(int pci, int pcf){

        try {

            //ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(Path.of("D:/Documents/Study/UFU/CURSOS/SFTP/sftp/src/main/resources/download/11/Users.csv")));

            //https://www.baeldung.com/opencsv

            List<String[]> lines = new ArrayList<>();
            CSVReader csvReader = new CSVReader(new FileReader("D:/Documents/Study/UFU/CURSOS/SFTP/sftp/src/main/resources/download/11/Users - Copia.csv"));
            lines = csvReader.readAll();
            System.out.println(lines.get(0)[0]);


        }catch (IOException e){
            e.printStackTrace();
        }catch (CsvException e){
            e.printStackTrace();
        }

    }

}
