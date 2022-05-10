package ru.kpfu.itis.bagautdinov.repositories;

import ru.kpfu.itis.bagautdinov.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FilesInfoRepository extends JpaRepository<FileInfo, Long> {

    @Query("select f from FileInfo f where f.storageFileName = ?1")
    Optional<FileInfo> findByStorageFileName(String fileName);
}

