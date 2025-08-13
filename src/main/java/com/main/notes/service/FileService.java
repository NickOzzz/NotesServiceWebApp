package com.main.notes.service;

import com.main.notes.dto.AccessLevels;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService implements IFileService
{
    @Value("${filesDirectory:./images/files/}")
    private String filesDirectory;

    public String CreateFile(MultipartFile file, String username, String receiver, boolean accessible) throws Exception
    {
        String fileNameWithId = null;
        if (file != null && file.getOriginalFilename() != null)
        {
            CreateDirectoryIfNotExists("");
            CreateDirectoryIfNotExists(username);

            String fileNamePrefix;
            if (accessible && receiver != null && !receiver.isEmpty())
            {
                fileNamePrefix = receiver;
            }
            else if (accessible)
            {
                fileNamePrefix = AccessLevels.All.toString();
            }
            else
            {
                fileNamePrefix = AccessLevels.None.toString();
            }

            fileNameWithId = fileNamePrefix + "-" + UUID.randomUUID() + file.getOriginalFilename();

            Path folderPathWithUsername = Paths.get(this.filesDirectory + username);
            Path fullFilePath = folderPathWithUsername.resolve(fileNameWithId);
            Files.copy(file.getInputStream(), fullFilePath);
        }
        return fileNameWithId;
    }

    public void CreateDirectoryIfNotExists(String username)
    {
        try
        {
            Files.createDirectory(Paths.get(this.filesDirectory + username));
        }
        catch(IOException ex)
        {
            System.out.println("Directory " + this.filesDirectory + username + " is already present. Skipping creation.");
        }
    }

    public String RenameFile(String fileName, String username, String receiver, boolean accessible)
    {
        if (fileName != null && !fileName.isEmpty() && !fileName.equals("None"))
        {
            String fileNamePrefix;
            if (accessible && receiver != null && !receiver.isEmpty())
            {
                fileNamePrefix = receiver;
            }
            else if (accessible)
            {
                fileNamePrefix = AccessLevels.All.toString();
            }
            else
            {
                fileNamePrefix = AccessLevels.None.toString();
            }

            String accessLevel = fileName.split("-")[0];
            String newFileName = fileName.substring(accessLevel.length() + 1);
            String newFileNameWithId = fileNamePrefix + "-" + newFileName;

            if (!fileName.equals(newFileNameWithId))
            {
                File fileToRename = new File(this.filesDirectory + username + "/" + fileName);
                boolean success = fileToRename.renameTo(new File(this.filesDirectory + username + "/" + newFileNameWithId));

                if (!success)
                {
                    System.out.println("Could not rename file. From: " + fileName + " To: " + newFileNameWithId);
                    return null;
                }
                return newFileNameWithId;
            }
        }
        return fileName;
    }

    public void DeleteFile(String fileName, String username) throws Exception
    {
        Path previousFilePath = Paths.get(this.filesDirectory + username + "/" + fileName);
        Files.deleteIfExists(previousFilePath);
    }

    public void DeleteDirectory(String username) throws Exception
    {
        FileUtils.deleteDirectory(new File(this.filesDirectory + username));
    }
}
