package org.koreait.file.services;

import lombok.RequiredArgsConstructor;
import org.koreait.file.controllers.RequestUpload;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 파일 업로드 기능
 */
@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileProperties properties;
    private final FileInfoRepository fileInfoRepository;

    public List<FileInfo> upload(RequestUpload form) {

        String gid = form.getGid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString(); // 랜덤, 중복없는 아이디 만들기

        String location = form.getLocation();
        MultipartFile[] files = form.getFiles();

        String rootPath = properties.getPath(); // 주 경로

        // 파일 업로드 성공 파일 정보
        List<FileInfo> uploadedItems = new ArrayList<>();

        for (MultipartFile file : files) {
            /* 1. 파일 업로드 정보 DB에 기록 S */

            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")); // 파일명.확장자 - 뒤에서부터 .찾고 .포함

            FileInfo item = new FileInfo();
            item.setGid(gid);
            item.setLocation(location);
            item.setFileName(fileName); // 처음 올린 파일명 그대로 가져오기
            item.setExtension(extension);
            item.setContentType(file.getContentType()); // 파일 형식 - image/png ...

            fileInfoRepository.saveAndFlush(item);

            /* 1. 파일 업로드 정보 DB에 기록 E */

            /* 2. 파일 업로드 처리 S */

            long seq = item.getSeq();
            String uploadFileName = seq + extension;
            long folder = seq % 10L; // 0~9
            File dir = new File(rootPath + folder);

            // 디렉토리가 존재하지 않거나 파일로만 있는 경우 디렉토리 생성
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }

            File _file = new File(dir, uploadFileName); // 서버에 실제로 올라갈 파일명

            try {
                // 파일 업로드 성공한 경우 DB 저장
                file.transferTo(_file);
                uploadedItems.add(item);

            } catch (IOException e) {
                // 파일 업로드 실패한 경우 DB 저장된 데이터 삭제
                fileInfoRepository.delete(item);
                fileInfoRepository.flush();
            }

            /* 2. 파일 업로드 처리 E */

        }

        return uploadedItems; // 성공 시 파일 업로드 정보 반환
    }
}