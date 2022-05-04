package com.mumusic.music.streaming.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Validate the mp3 file.
 */
public interface FileValidationService {

    void validate(MultipartFile file) throws Exception;
}
