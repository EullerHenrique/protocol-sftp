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
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(sftpService.download(f));
    }

}
