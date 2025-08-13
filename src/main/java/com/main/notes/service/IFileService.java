package com.main.notes.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService
{
    String CreateFile(MultipartFile file, String username, String receiver, boolean accessible) throws Exception;
    void CreateDirectoryIfNotExists(String username);
    String RenameFile(String fileName, String username, String receiver, boolean accessible);
    void DeleteFile(String fileName, String username) throws Exception;
    void DeleteDirectory(String username) throws Exception;
}
