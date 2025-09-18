package com.models;

import java.time.LocalDate;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Task {
    
    private static int idCounter = 1;
    private int taskId;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    private String description;
    private Status status;

    public Task(String description, Status status) {
        this.taskId = idCounter++;
        this.description = description;
        this.status = status;
    }

       @JsonCreator
    public Task(@JsonProperty("taskId") int taskId,
                @JsonProperty("description") String description,
                @JsonProperty("status") Status status,
                @JsonProperty("createdAt") LocalDate createdAt,
                @JsonProperty("updatedAt") LocalDate updatedAt) {
      
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }   
      @Override
    public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("taskId=" + taskId)
                .add("description='" + description + "'")
                .add("status=" + status)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }
}
