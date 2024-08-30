package com.example.taskify.Task.presentation.AddTask;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskify.Task.data.TaskRepository;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTaskViewModel extends ViewModel {

    private final TaskRepository repo;
    private final MutableLiveData<Response<Integer, Failure>> _addNoteEventListener = new MutableLiveData<>();
    public LiveData<Response<Integer, Failure>> addNoteEventListener = _addNoteEventListener;

    @Inject
    public AddTaskViewModel(TaskRepository repo) {
        this.repo = repo;
    }

    public void AddNoteEvent(TaskItem taskItem) {  //setting the current userId before saving the record
        Log.d("Creating Note", taskItem.toString());
        if (taskItem.getTaskId() == 0) {
            _addNoteEventListener.setValue(repo.createTask(taskItem));
        } else {
            _addNoteEventListener.setValue(repo.updateNote(taskItem));
        }
    }
}
