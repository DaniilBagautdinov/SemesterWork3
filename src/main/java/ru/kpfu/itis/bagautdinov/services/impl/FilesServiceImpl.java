package ru.kpfu.itis.bagautdinov.services.impl;

import ru.kpfu.itis.bagautdinov.models.FileInfo;
import ru.kpfu.itis.bagautdinov.repositories.FilesInfoRepository;
import ru.kpfu.itis.bagautdinov.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    @Value("${files.storage.path}")
    private String storagePath;

    @Value("${files.url}")
    private String filesUrl;

    private final FilesInfoRepository filesInfoRepository;


    @Override
    public FileInfo upload(MultipartFile multipart) {
        try {
            String extension = Objects.requireNonNull(multipart.getOriginalFilename()).substring(multipart.getOriginalFilename().lastIndexOf("."));

            String storageFileName = UUID.randomUUID() + extension;

            FileInfo file = FileInfo.builder()
                    .type(multipart.getContentType())
                    .originalFileName(multipart.getOriginalFilename())
                    .storageFileName(storageFileName)
                    .size(multipart.getSize())
                    .build();

            Files.copy(multipart.getInputStream(), Paths.get(storagePath, file.getStorageFileName()));


            filesInfoRepository.save(file);

            return file;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public List<FileInfo> upload(MultipartFile[] multipart) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for (MultipartFile multipartFile : multipart) {
            fileInfoList.add(upload(multipartFile));
        }
        return fileInfoList;
    }

    @Override
    public void addFileToResponse(String fileName, HttpServletResponse response) {
        FileInfo file = filesInfoRepository.findByStorageFileName(fileName).orElseThrow();
        response.setContentLength(file.getSize().intValue());
        response.setContentType(file.getType());
        response.setHeader("Content-Disposition", "filename=\"" + file.getOriginalFileName() + "\"");
        try {
            IOUtils.copy(new FileInputStream(storagePath + "/" + file.getStorageFileName()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
