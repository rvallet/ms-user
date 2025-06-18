package com.vallet.ms_user.model;

import com.vallet.ms_user.enums.CreatorType;

import java.time.LocalDateTime;

public class CommonData {
    private CreatorType createdBy;
    private CreatorType updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommonData() {
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
        setCreatedBy(CreatorType.SYSTEM);
        setUpdatedBy(CreatorType.SYSTEM);
    }

    public CommonData(CreatorType createdBy, CreatorType updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CreatorType getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatorType createdBy) {
        this.createdBy = createdBy;
    }

    public CreatorType getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(CreatorType updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
