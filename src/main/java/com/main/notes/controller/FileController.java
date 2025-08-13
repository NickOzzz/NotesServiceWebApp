package com.main.notes.controller;

import com.main.notes.dto.AccessLevels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping("/notes/files")
public class FileController {
    @Value("${filesDirectory:./images/files/}")
    private String filesDirectory;

    @GetMapping("/{username}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String username, @PathVariable String fileName, Principal user)
    {
        String fileNamePrefix = fileName.split("-")[0];
        if (user == null
                || (!fileNamePrefix.equals(AccessLevels.All.toString())
                    && !fileNamePrefix.equals(user.getName())
                    && !(fileNamePrefix.equals(AccessLevels.None.toString()) && user.getName().equals(username))
                    && !username.equals(user.getName())))
        {
            return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }

        try
        {
            Path filePath = Paths.get(this.filesDirectory + username + "/" + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            String mediaType = Files.probeContentType(filePath);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mediaType)).body(resource);
        }
        catch(Exception ex)
        {
            System.out.println("Could not get file: " + ex.getMessage());
            return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
        }
    }
}
