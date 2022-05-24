package com.euller.sftp.controller;

import com.euller.sftp.service.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SftpController {

    private final SftpService sftpService;

    @GetMapping("/download")
    public ResponseEntity<String> download (){
        return ResponseEntity.ok().body(sftpService.download());
    }

}
