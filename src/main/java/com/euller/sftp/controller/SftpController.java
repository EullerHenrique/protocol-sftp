package com.euller.sftp.controller;

import com.euller.sftp.service.sftp.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SftpController {

    private final SftpService sftpService;

    @GetMapping("/download/{f}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("f") String f){

        return ResponseEntity
                .ok()
                //application/octet-stream: application/octet-stream: Este é o valor padrão para um arquivo binario.
                //Um tipo de arquivo desconhecido deveria usar este tipo.
                //Eles tratam-na como se o cabeçalho Content-Disposition fosse definido com o anexo de valor e
                //propusessem um "Salvar como".É um tipo padrão para todos outros casos.
                //-> header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+f);
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(sftpService.download(f));
    }

}
