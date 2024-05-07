package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.service.FileServiceImp;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    FileServiceImp fileServiceImp;

    public FileController(FileServiceImp fileServiceImp) {
        this.fileServiceImp = fileServiceImp;
    }

//    @GetMapping(value = "/downloadPDF/{id}")
//    public ResponseEntity<Resource> getPDFFile(@PathVariable Long id) throws IOException {
//        return fileServiceImp.getPDFFile(id);
//    }

    @GetMapping(value = "/downloadPDF/{id}")
    public @ResponseBody byte[] getPDFFile(@PathVariable Long id) throws IOException {
        return fileServiceImp.getFileById(id);
    }

    @GetMapping(value = "/downloadAudio/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getAudioFile(@PathVariable Long id) throws IOException {
        return fileServiceImp.getFileById(id);
    }
}
