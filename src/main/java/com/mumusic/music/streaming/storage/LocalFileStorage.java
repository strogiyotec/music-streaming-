package com.mumusic.music.streaming.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Stores files in local storage.
 */
@AllArgsConstructor
@Service
public final class LocalFileStorage implements FileStorage {

    private final ConcurrentSkipListSet<String> storage;

    private final String storagePath;

    @Autowired
    public LocalFileStorage(@Value("${storage-path}") final String storagePath) throws IOException {
        this.storage = new ConcurrentSkipListSet<>();
        this.storagePath = storagePath;
        //create this dir if not exist.
        final Path path = Paths.get(this.storagePath);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        //populate database.
        Files.walk(path)
            .filter(Files::isRegularFile)
            .forEach(file -> this.storage.add(file.getFileName().toString()));
    }

    @Override
    public void store(final MultipartFile file) throws Exception {
        final String randomName = LocalFileStorage.generateRandomName(file);
        final Path localFile = Files.createFile(Paths.get(this.storagePath, randomName));
        file.transferTo(localFile);
        this.storage.add(randomName);
    }

    @Override
    public Set<String> getAll() {
        return Collections.unmodifiableSortedSet(this.storage);
    }

    @Override
    public Path getOne(final String name) {
        if (!this.storage.contains(name)) {
            throw new IllegalArgumentException(
                String.format(
                    "No files with name %s",
                    name
                )
            );
        }
        final Path path = Paths.get(this.storagePath, name);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(
                String.format(
                    "File %s was deleted",
                    name
                )
            );
        }
        return path;
    }

    @Override
    public void delete(final String fileName) throws IOException {
        this.storage.remove(fileName);
        final Path path = Paths.get(this.storagePath, fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(
                String.format(
                    "File %s was deleted",
                    fileName
                )
            );
        }
        Files.delete(path);
    }

    private static String generateRandomName(final MultipartFile file) {
        return file.getName() + UUID.randomUUID() + '.' + StringUtils.getFilenameExtension(file.getOriginalFilename());
    }
}
