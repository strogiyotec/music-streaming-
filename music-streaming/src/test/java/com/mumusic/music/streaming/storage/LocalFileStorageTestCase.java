package com.mumusic.music.streaming.storage;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

public final class LocalFileStorageTestCase {

    @Test
    void testStorage(@TempDir Path tempDir) throws Exception {
        var fileName = "test.mp3";
        var storage = new LocalFileStorage(tempDir.toAbsolutePath().normalize().toString());
        //when
        storage.store(new MockMultipartFile("test", fileName, "mp3", "Content".getBytes(StandardCharsets.UTF_8)));
        //then
        final Set<String> savedFiles = storage.getAll();
        Assertions.assertEquals(savedFiles.size(), 1);
        //and then
        final Path file = storage.getOne(savedFiles.iterator().next());
        Assertions.assertTrue(Files.exists(file));
    }
}
