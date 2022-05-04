package com.mumusic.music.streaming.rest;

import com.mumusic.music.streaming.service.FileService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Http endpoints for Music Player.
 */
@RestController
@AllArgsConstructor
@CrossOrigin
public class MusicController {

    private final FileService fileService;

    @PostMapping("/file")
    ResponseEntity<?> uploadFile(@RequestParam("file") final MultipartFile file) {
        try {
            this.fileService.saveFile(file);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (final Exception exc) {
            return new ResponseEntity<>(
                Map.of("error", exc.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/files/{id}")
    ResponseEntity<?> getFile(@PathVariable("id") final String id) throws Exception {
        final Resource file = this.fileService.load(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + '"').body(file);
    }

    @DeleteMapping("/files/{id}")
    @CrossOrigin("*")
    ResponseEntity<?> deleteFile(@PathVariable("id") final String id) {
        try {
            this.fileService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (final Exception exc) {
            return new ResponseEntity<>(
                Map.of("error", exc.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/files")
    ResponseEntity<?> getFiles() {
        try {
            return ResponseEntity.ok(this.fileService.getFiles());
        } catch (final Exception exc) {
            return new ResponseEntity<>(
                Map.of("error", exc.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
