package com.example.taskify.Task.domain;

import java.util.Date;

public class TaskItem {
    private String title;
    private String description;
    private int taskId;
    private String priority;

    private Date createdOn;

    public TaskItem() {
    }

    public TaskItem(int taskId, String title, String description) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdOn = new Date();
    }

    public TaskItem(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdOn = new Date();
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
