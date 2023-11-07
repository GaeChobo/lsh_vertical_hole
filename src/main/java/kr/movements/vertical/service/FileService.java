package kr.movements.vertical.service;

import kr.movements.vertical.dto.FileListResponse;
import kr.movements.vertical.entity.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface FileService {

    ResponseEntity<byte[]> processFiles(Long verticalId, MultipartFile multipartFile, String floorName);

    ResponseEntity<Resource> downloadImage(Long imgFileId) throws IOException;

    ResponseEntity<Resource> downloadFile(byte[] fileData, String fileName);

    FileEntity saveFile(MultipartFile mFile, String s3DirPath);
    FileEntity updateFile(MultipartFile mFile, Long fileId);

    void imgFileCheck(MultipartFile imgFile);

    //FileEntity saveFile(InputStream fileStream, String s3DirPath, String fileName, Long customId);

    //FileEntity saveImg(MultipartFile image, String s3DirPath, Long customId);
    //FileEntity saveImgWithThumbnail(MultipartFile image, String s3DirPath, Long customId);
    FileEntity updateImg(MultipartFile image, Long fileId);
    FileEntity updateImgWithThumbnail(MultipartFile image, Long fileId, String oldStoredName);
    void deleteImg(Long fileId);
    byte[] getThumbnail(Long imgFileId) throws IOException;
    byte[] getImage(Long imgFileId) throws IOException;



}