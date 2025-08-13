package com.main.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
		"com.main.notes.controller",
		"com.main.notes.service",
		"com.main.notes.aspect",
        "com.main.notes.configuration",
		"com.main.notes.advice",
		"com.main.notes.dto",
		"com.main.notes.event",
		"com.main.notes.exception",})
public class NotesApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

}
