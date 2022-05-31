package com.euller.sftp.test;

import com.euller.sftp.controller.SftpController;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@AllArgsConstructor
public class Test {

    private final SftpController sftpController;

    //Teste de Volume
    //@Bean
    public  void testeVolume(){

        LocalDateTime inicio = LocalDateTime.now();
        System.out.println("Início: "+inicio);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Boolean>> callableTasks = new ArrayList<>();
        Callable<Boolean> callableTask;

        for(int i=0; i < 10; i++){
            int ii = i+1;
            callableTask = () -> sftpController.download(ii+"");
            callableTasks.add(callableTask);
        }

        List<Future<Boolean>> futures;
        try {
            futures =  executorService.invokeAll(callableTasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean result = futures.stream().allMatch(b ->{
            try {
                return b.get() == true;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

       if(result) {

           LocalDateTime fim = LocalDateTime.now();
           System.out.println("Fim: " + fim);

           long millis = inicio.until(fim, ChronoUnit.MILLIS);

           long hours = TimeUnit.MILLISECONDS.toHours(millis);
           long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours) ;
           long secondes = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes);


           System.out.println("Tempo decorrido: " + hours + ":" + minutes + ":" + secondes);

       }

    }

    @Bean
    //Teste de Manipulação do CSV
    public void testeManipulacao(){

        sftpController.manipulateCSV(3, 6);

    }
}
