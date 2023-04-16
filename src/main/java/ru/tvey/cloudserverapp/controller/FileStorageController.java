package ru.tvey.cloudserverapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tvey.cloudserverapp.entity.FileData;
import ru.tvey.cloudserverapp.response.ResponseData;
import ru.tvey.cloudserverapp.service.file.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/cloud/storage")
@AllArgsConstructor
public class FileStorageController {

    private FileService fileService;

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,
                                   Authentication auth) throws Exception {
        FileData fileData = fileService.saveFile(file, auth);
        String downloadURL = "";
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/download/")
                .path(String.valueOf(fileData.getId()))
                .toUriString();

        return new ResponseData(fileData.getName(),
                downloadURL,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId,
                                          Authentication auth) throws Exception {

        FileData fileData = fileService.serveFile(fileId, auth);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileData.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileData.getName()
                                + "\"")
                .body(new ByteArrayResource(fileData.getContent()));
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId, Authentication auth) throws IOException {
        return new ResponseEntity<>(fileService.deleteFile(fileId, auth),
                HttpStatus.NO_CONTENT);
    }

}
