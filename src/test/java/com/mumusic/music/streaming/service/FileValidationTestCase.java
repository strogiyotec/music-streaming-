package com.mumusic.music.streaming.service;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public final class FileValidationTestCase {

    @Test
    void testValidation() {
        final FileValidationImpl validation = new FileValidationImpl(Set.of("mp3"), 10L);
        final String fileName = "test.jpg";
        Assertions.assertThrows(IllegalArgumentException.class, () -> validation.validate(new MockMultipartFile("test", fileName, "mp3", "Content".getBytes(StandardCharsets.UTF_8))));
    }
}
