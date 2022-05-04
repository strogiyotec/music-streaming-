package com.mumusic.music.streaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main.
 */
@SpringBootApplication(proxyBeanMethods = false)
public class MusicStreamingApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MusicStreamingApplication.class, args);
	}

}
