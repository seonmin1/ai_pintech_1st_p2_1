package org.koreait.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
/**
 * 공통 속성 추상클래스로 정의
 */
@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 날짜와 시간 정의 시 필수 애노테이션
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 등록일시

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedAt; // 수정일시

    private LocalDateTime deletedAt; // 삭제일시
}