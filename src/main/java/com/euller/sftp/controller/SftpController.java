package com.euller.sftp.controller;

import com.euller.sftp.service.sftp.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SftpController {

    private final SftpService sftpService;

    @GetMapping("/download/{d}")
    public Boolean download(@PathVariable("d") String d)  {

      return sftpService.download(d);
    }

    @GetMapping("/manipulateCSV/{pci}/{pcf}")
    public void manipulateCSV(@PathVariable("pci") int pci, @PathVariable("pcf") int pcf)  {

        sftpService.manipulateCSV(pci, pcf);

    }

}
