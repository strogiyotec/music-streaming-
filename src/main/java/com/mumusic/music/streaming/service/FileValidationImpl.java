package com.mumusic.music.streaming.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationImpl implements FileValidationService {

    private final Set<String> acceptableFileExtensions;

    private final long maxSize;

    public FileValidationImpl(
        @Value("#{'${available-extensions}'.split(';')}") final Set<String> acceptableFileExtensions,
        @Value("${max-size}") final long maxSize
    ) {
        this.acceptableFileExtensions = acceptableFileExtensions;
        this.maxSize = maxSize;
    }

    @Override
    public void validate(final MultipartFile file) {
        //validate extension
        final String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!this.acceptableFileExtensions.contains(filenameExtension)) {
            throw new IllegalArgumentException(
                String.format(
                    "Extension %s is not supported",
                    filenameExtension
                )
            );
        }
        final long size = file.getSize();
        if (size > this.maxSize) {
            throw new IllegalArgumentException(
                "The file size is too big"
            );
        }
    }
}
