package ru.tvey.cloudserverapp.service.file;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.tvey.cloudserverapp.entity.FileData;

import java.io.IOException;

public interface FileService {
    FileData saveFile(MultipartFile file, Authentication auth) throws Exception;

    FileData serveFile(Long id, Authentication authentication) throws Exception;

    String deleteFile(Long fileId, Authentication authentication) throws IOException;

}
