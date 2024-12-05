package org.koreait.global.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor // 기본 생성자 생성
public class JSONData {

    private HttpStatus status = HttpStatus.OK; // 응답코드, OK 고정
    private boolean success = true; // 성공여부, true 고정
    private Object message; // 실패시 에러 메세지, 형태가 여러가지일 수 있으므로 Object 자료형 사용
    private Object data; //  성공 시 데이터, 형태가 여러가지일 수 있으므로 Object 자료형 사용

    // 기본 생성자
    public JSONData(Object data) {
        this.data = data;
    }
}