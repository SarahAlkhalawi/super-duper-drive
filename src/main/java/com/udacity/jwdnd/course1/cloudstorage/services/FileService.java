package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

//    public Integer addFile(MultipartFile file, Integer userid){
//        try {
//            return fileMapper.addFile(new File(file.getOriginalFilename(),file.getContentType(),
//                    Long.toString(file.getSize()),userid,file.getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }

    public Integer addFile(File file, Integer userid){
        return fileMapper.addFile(new File(file.getFilename(),file.getContenttype(),
                    file.getFilesize(),userid,file.getFiledata()));
    }

    public Integer deleteFile(Integer fileId){
        return fileMapper.deletefile(fileId);
    }

    public List<File> getAllFiles(Integer userid){
        return fileMapper.getFiles(userid);
    }

    public Boolean isFilenameExist(Integer userid, String filename){
        return fileMapper.getFile(userid,filename) != null;
    }

    public File getFile(String  filename, Integer userid){
        return fileMapper.getFile(userid, filename);
    }
}
