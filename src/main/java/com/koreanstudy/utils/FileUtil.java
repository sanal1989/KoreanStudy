package com.koreanstudy.utils;

import com.koreanstudy.entity.File;
import com.koreanstudy.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUtil {

    FileRepository fileRepository;

    @Value("${path.folder}")
    private String pathFolder;

    public FileUtil(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File saveFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path uploadPath = Paths.get(pathFolder);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create directory");
            }
        }
        String fileCode = UUID.randomUUID().toString();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode+"."+extension);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new RuntimeException("Could not save file: " + fileName);
        }
        File response = new File();
        response.setType(multipartFile.getContentType());
        response.setOriginalTitle(fileName);
        response.setPath(StringUtils.cleanPath(uploadPath.toString()+"\\"+fileCode+"."+extension));
        fileRepository.save(response);
        return response;
    }

//    public Resource getFileAsResource(String path) throws IOException {
//        Path dirPath = Paths.get("pathFolder");
//        AtomicReference<Path> foundFile = new AtomicReference<>();
//        Files.list(dirPath).forEach(file -> {
//            if (file.getFileName().toString().startsWith(path)) {
//                foundFile.set(file);
//                return;
//            }
//        });
//        if (foundFile.get() != null) {
//            return new UrlResource(foundFile.get().toUri());
//        }
//        return null;
//    }
}
