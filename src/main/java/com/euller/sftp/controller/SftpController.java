package com.euller.sftp.controller;

import com.euller.sftp.service.sftp.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SftpController {

    private final SftpService sftpService;

    @GetMapping("/download/{d}/{f}")
    public ResponseEntity<Boolean> download(@PathVariable("d") String d, @PathVariable("f") String f)  {

        return ResponseEntity
                .ok()
                .body(sftpService.download(d + "/" + f));
    }

}
