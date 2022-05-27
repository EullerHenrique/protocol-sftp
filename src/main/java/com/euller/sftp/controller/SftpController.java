package com.euller.sftp.controller;

import com.euller.sftp.service.sftp.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SftpController {

    private final SftpService sftpService;

    @GetMapping("/download/{d}")
    public ResponseEntity<Boolean> download(@PathVariable("d") String d)  {
        return ResponseEntity
                .ok()
                .body(sftpService.download(d));
    }

}
