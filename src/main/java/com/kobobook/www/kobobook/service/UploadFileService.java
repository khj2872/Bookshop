package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.UploadFile;
import com.kobobook.www.kobobook.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadFileService {

    @Autowired
    UploadFileRepository uploadFileRepository;

    /*
    *
    * */
    @Transactional
    public UploadFile create(String originalFilename, String storedFileName, String path) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriginalFileName(originalFilename);
        uploadFile.setStoredFileName(storedFileName);
        uploadFile.setPath(path);

        return uploadFileRepository.save(uploadFile);
    }

}
