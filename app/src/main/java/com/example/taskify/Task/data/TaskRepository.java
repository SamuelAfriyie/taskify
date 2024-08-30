package com.example.taskify.Task.data;

import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;

import java.util.List;

public interface TaskRepository {
    public Response<Integer, Failure> createTask(TaskItem task);
    public Response<Integer, Failure> updateNote(TaskItem task);
    public Response<List<TaskItem>, Failure> fetchAll();
    public Response<List<TaskItem>, Failure> filterByTitle( String title);
    public Response<List<TaskItem>, Failure> filterByPriority( String priority);
    public Response<Integer, Failure> deleteNote(int taskId);
}
