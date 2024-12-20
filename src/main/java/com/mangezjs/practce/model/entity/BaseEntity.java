package com.mangezjs.practce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity{
    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt; // 생성 시간

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 시간

    @Column(name = "removed_at")
    private LocalDateTime removedAt; // 삭제 시간

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy; // 생성자

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy; // 수정자

    @PrePersist
    protected void onPrePersist() {
        this.registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 삭제 시간을 설정하는 메서드
    public void setRemovedAt() {
        this.removedAt = LocalDateTime.now();
    }
}
