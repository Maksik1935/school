package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.AvatarNorFoundException;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarsDir, student.getName() + getFileExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try(InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(is,1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
        logger.info("Invoked UploadAvatar");
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("." + 1));
    }

    public Avatar findAvatar(long id) {
        logger.info("Invoked FindAvatar");
        return avatarRepository.findByStudentId(id).orElseThrow(AvatarNorFoundException::new);
    }

    public List<Avatar> getAllAvatars(int page, int size) {
        logger.info("Invoked GetAllAvatar");
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
