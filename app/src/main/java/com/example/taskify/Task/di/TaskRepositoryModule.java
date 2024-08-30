package com.example.taskify.Task.di;

import android.content.Context;

import com.example.taskify.Task.data.TaskRepository;
import com.example.taskify.Task.data.TaskRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TaskRepositoryModule {
    @Provides
    public TaskRepository provideNoteRepository(@ApplicationContext Context context) {
        return new TaskRepositoryImpl(context);
    }
}
