package com.mumusic.music.streaming.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    /**
     * Store given file.
     *
     */
    void store(MultipartFile file) throws Exception;

    /**
     * Get a list of all files names.
     */
    Set<String> getAll() throws Exception;

    Path getOne(String name) throws Exception;

    /**
     * Delete file by filename.
     */
    void delete(String fileName) throws Exception;
}
