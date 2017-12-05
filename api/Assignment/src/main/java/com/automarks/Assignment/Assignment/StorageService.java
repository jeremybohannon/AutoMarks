package com.automarks.Assignment.Assignment;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class StorageService {

    private final Path rootLocation = Paths.get("temp");

    public void store(MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public File getFileByUrl(String url, String fileName, String folder){
        try{
            if(!folder.isEmpty() || folder.trim().length() >= 1) {
                FileUtils.copyURLToFile(new URL(url), new File(this.rootLocation.resolve(folder + "/" + fileName).toString()));
                fileName = folder + "/" + fileName;
            }else{
                FileUtils.copyURLToFile(new URL(url), new File(this.rootLocation.resolve(fileName).toString()));
            }
        }catch (Exception e){
            throw new RuntimeException("FAIL!");
        }
        return getFile(fileName);
    }

    public File getFile(String filename){
        try{
            Path f = rootLocation.resolve(filename);
            File file = new File(String.valueOf(f));
            if(file.exists() || file.canRead()){
                return file;
            }else{
                throw new RuntimeException("FAIL!");
            }
        }catch (Exception e){
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteFile(String fileName) {
        File file = getFile(fileName);
        if(file.exists()){
            if(file.delete()){
                System.out.println("File deleted successfully");
            }else{
                System.out.println("Fail to delete file");
            }
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}
