package ru.tvey.cloudserverapp.service.file;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.tvey.cloudserverapp.entity.FileData;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.exception.file.FileIsEmptyException;
import ru.tvey.cloudserverapp.exception.file.FileNotFoundException;
import ru.tvey.cloudserverapp.exception.file.FileSaveException;
import ru.tvey.cloudserverapp.exception.file.UserNotOwnerException;
import ru.tvey.cloudserverapp.repository.FileDataRepository;
import ru.tvey.cloudserverapp.repository.UserRepository;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileDataRepository fileDataRepository;

    private UserRepository userRepository;


    @Override
    public FileData saveFile(MultipartFile file, Authentication auth) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename is invalid");
            }
            if (!file.isEmpty()) {
                FileData fileData = new FileData();
                fileData.setContent(file.getBytes());
                fileData.setName(fileName);
                fileData.setFileType(Objects.requireNonNull(file.getContentType()));
                fileData.setOwnerName(unwrapUser(userRepository.findByUsername(auth.getName())));
                return fileDataRepository.save(fileData);
            }else {
                throw new FileIsEmptyException("File cannot be empty");
            }
        } catch (Exception e) {
            throw new FileSaveException("Couldn't save file " + fileName + " error: " + e.getMessage());
        }
    }

    @Override
    public FileData serveFile(Long fileId, Authentication auth) throws Exception {
        FileData fileData = unwrapFileData(fileDataRepository.findById(fileId));
        ownerMatchesUser(fileData, auth);

        return fileData;
    }

    @Override
    public String deleteFile(Long fileId, Authentication auth) {

        FileData fileData = unwrapFileData(fileDataRepository.findById(fileId));

        if (ownerMatchesUser(fileData, auth)) {
            fileDataRepository.deleteById(fileId);
        }
        return "You have successfully deleted file " + fileData.getName();

    }

    private FileData unwrapFileData(Optional<FileData> file) {
        if (file.isPresent()) return file.get();
        else throw new FileNotFoundException("File not found");
    }

    private User unwrapUser(Optional<User> user) {
        if (user.isPresent()) return user.get();
        else throw new RuntimeException("User not found");
    }

    private boolean ownerMatchesUser(FileData fileData, Authentication auth) {

        if (!(fileData.getOwnerName().getUsername().equals(auth.getName()))) {
            throw new UserNotOwnerException(auth.getName() + " do not own " + fileData.getName());
        }
        return true;
    }
}
