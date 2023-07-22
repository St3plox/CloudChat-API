package ru.tvey.cloudserverapp.service.file;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.tvey.cloudserverapp.entity.FileData;
import ru.tvey.cloudserverapp.entity.user.User;
import ru.tvey.cloudserverapp.exception.file.FileIsEmptyException;
import ru.tvey.cloudserverapp.exception.file.FileSaveException;
import ru.tvey.cloudserverapp.exception.user.UserNotOwnerException;
import ru.tvey.cloudserverapp.repository.FileDataRepository;
import ru.tvey.cloudserverapp.repository.UserRepository;
import ru.tvey.cloudserverapp.service.EntityService;

import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    private final FileDataRepository fileDataRepository;

    private final UserRepository userRepository;

    private final EntityService entityService;

    public FileServiceImpl(FileDataRepository fileDataRepository, UserRepository userRepository, EntityService entityService) {
        this.fileDataRepository = fileDataRepository;
        this.userRepository = userRepository;
        this.entityService = entityService;
    }

    @Override
    public FileData saveFile(MultipartFile file, Authentication auth) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new FileSaveException("Filename is invalid");
            }
            if (!file.isEmpty()) {

                FileData fileData = new FileData();
                fileData.setContent(file.getBytes());
                fileData.setName(fileName);
                fileData.setFileType(Objects.requireNonNull(file.getContentType()));
                fileData.setOwnerName((User) entityService.unwrapEntity(userRepository
                        .findByUsername(auth.getName())));

                return fileDataRepository.save(fileData);
            } else {
                throw new FileIsEmptyException("File cannot be empty");
            }
        } catch (Exception e) {
            throw new FileSaveException("Couldn't save file " + fileName + " error: " + e.getMessage());
        }
    }

    @Override
    public FileData serveFile(Long fileId, Authentication auth) {
        FileData fileData = (FileData) entityService.unwrapEntity(fileDataRepository.findById(fileId));
        ownerMatchesUser(fileData, auth);
        return fileData;
    }

    @Override
    public String deleteFile(Long fileId, Authentication auth) {

        FileData fileData = (FileData) entityService.unwrapEntity(fileDataRepository.findById(fileId));

        if (ownerMatchesUser(fileData, auth)) {
            fileDataRepository.deleteById(fileId);
        }
        return "You have successfully deleted file " + fileData.getName();

    }

    private boolean ownerMatchesUser(FileData fileData, Authentication auth) {

        if (!(fileData.getOwnerName().getUsername().equals(auth.getName()))) {
            throw new UserNotOwnerException(auth.getName() + " do not own " + fileData.getName());
        }
        return true;
    }
}
