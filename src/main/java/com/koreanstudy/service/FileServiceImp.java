package com.koreanstudy.service;

import com.koreanstudy.entity.File;
import com.koreanstudy.repository.FileRepository;
import com.koreanstudy.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImp implements FileService {

    FileRepository fileRepository;
    ResourceLoader resourceLoader;

    @Value("${path.folder}")
    private String pathFolder;

    public FileServiceImp(FileRepository fileRepository,
                          ResourceLoader resourceLoader) {
        this.fileRepository = fileRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public byte[] getFileById(Long id) throws IOException {
        File file;
        try {
            file = fileRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("File not found for id :" + id));
        }catch (RuntimeException e){
            return null;
        }
        return Files.readAllBytes(Paths.get(file.getPath()));
    }

    public ResponseEntity<Resource> getPDFFile(Long id){
        try {
            File file1 = fileRepository.findById(id).get();
            Resource resource = resourceLoader.getResource("file:/"+file1.getPath());
            java.io.File file = resource.getFile();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                    .body(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
