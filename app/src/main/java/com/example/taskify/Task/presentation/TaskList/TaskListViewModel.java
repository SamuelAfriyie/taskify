package com.example.taskify.Task.presentation.TaskList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskify.Task.data.TaskRepository;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskListViewModel extends ViewModel {
    private final TaskRepository taskRepository;
    private final MutableLiveData<Response<List<TaskItem>, Failure>> tasks = new MutableLiveData<>();
    private final MutableLiveData<Response<Integer, Failure>> _deletionStatus = new MutableLiveData<>();
    public final LiveData<Response<Integer, Failure>> deletionStatus = _deletionStatus;

    @Inject
    public TaskListViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        fetchAllTask();
    }

    public LiveData<Response<List<TaskItem>, Failure>> getTask() {
        return tasks;
    }


    public void fetchAllTask() {
        tasks.setValue(taskRepository.fetchAll());
    }
    public void filterAllTask(String filteredText) {
        tasks.setValue(taskRepository.filterByTitle(filteredText));
    }
 public void filterAllTaskByPriority(String priority) {
        tasks.setValue(taskRepository.filterByPriority(priority));
    }

    public void deleteNote(int noteId) {
        _deletionStatus.setValue(taskRepository.deleteNote(noteId));
    }
}
