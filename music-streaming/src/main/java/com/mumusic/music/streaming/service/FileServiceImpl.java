package com.mumusic.music.streaming.service;

import com.mumusic.music.streaming.storage.FileStorage;
import java.nio.file.Path;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public final class FileServiceImpl implements FileService {

    private final FileValidationService validationService;

    private final FileStorage fileStorage;

    @Override
    public void saveFile(final MultipartFile file) throws Exception {
        this.validationService.validate(file);
        this.fileStorage.store(file);
    }

    @Override
    public Set<String> getFiles() throws Exception {
        return this.fileStorage.getAll();
    }

    @Override
    public Resource load(final String id) throws Exception {
        final Path file = this.fileStorage.getOne(id);
        return new UrlResource(file.toUri());
    }

    @Override
    public void delete(final String id) throws Exception {
        this.fileStorage.delete(id);
    }
}
