package org.koreait.file.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestUpload {

    @NotBlank
    private String gid;
    private String location;

    private boolean single; // 단일 파일
    private boolean imageOnly; // 이미지 형식 파일만 허용

    public MultipartFile[] files; // 파일이 여러개일 수 있으므로 배열 형태
}
