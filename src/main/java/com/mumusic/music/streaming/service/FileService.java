package com.mumusic.music.streaming.service;

import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveFile(MultipartFile file) throws Exception;

    Set<String> getFiles() throws Exception;

    Resource load(String id) throws Exception;

    void delete(String id) throws Exception;
}
