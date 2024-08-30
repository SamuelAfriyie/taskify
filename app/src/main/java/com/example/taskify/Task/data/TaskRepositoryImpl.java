package com.example.taskify.Task.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.taskify.Task.domain.DatabaseNotFound;
import com.example.taskify.Task.domain.TaskItem;
import com.example.taskify.core.data.Dao;
import com.example.taskify.core.failures.Failure;
import com.example.taskify.core.utils.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository{
    private final Dao taskDao;

    public TaskRepositoryImpl(Context context) {
        this.taskDao = new Dao(context);
    }

    @Override
    public Response<Integer, Failure> createTask(TaskItem task) {
        try {

            SQLiteDatabase db = taskDao.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Dao.COLUMN_TASK_TITLE, task.getTitle());
            values.put(Dao.COLUMN_TASK_DESC, task.getDescription());
            values.put(Dao.COLUMN_TASK_PRIORITY, task.getPriority());
            values.put(Dao.COLUMN_TASK_DATE, task.getCreatedOn().toString());
            Log.d("DatabaseDebug", "Inserting user into database...");
            long result = db.insert(Dao.TABLE_TASK, null, values);
            Log.d("DatabaseDebug", "Insert result: " + result);
            db.close();
            return result != -1 ? Response.success(1) : Response.failure(new DatabaseNotFound("FailedToCreate", "Note creation failed"));
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<Integer, Failure> updateNote(TaskItem task) {
        try {
            SQLiteDatabase db = taskDao.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Add updated values to the ContentValues
            values.put(Dao.COLUMN_TASK_TITLE, task.getTitle());
            values.put(Dao.COLUMN_TASK_DESC, task.getDescription());
            values.put(Dao.COLUMN_TASK_PRIORITY, task.getCreatedOn().toString());
            values.put(Dao.COLUMN_TASK_DATE, task.getCreatedOn().toString());

            // Define the WHERE clause and arguments
            String whereClause = Dao.COLUMN_TASK_ID + " = ?";
            String[] whereArgs = {String.valueOf(task.getTaskId())};

            // Log the start of the update operation
            Log.d("DatabaseDebug", "Updating note with noteId: " + task.getTaskId());
            int rowsAffected = db.update(Dao.TABLE_TASK, values, whereClause, whereArgs);
            Log.d("DatabaseDebug", "Rows affected: " + rowsAffected);
            db.close();

            return Response.success(rowsAffected);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<List<TaskItem>, Failure> fetchAll() {
        try {
            List<TaskItem> notes = new ArrayList<>();
            SQLiteDatabase db = taskDao.getReadableDatabase();

            // Define the query
            String[] columns = {
                    Dao.COLUMN_TASK_ID,
                    Dao.COLUMN_TASK_TITLE,
                    Dao.COLUMN_TASK_DESC,
                    Dao.COLUMN_TASK_PRIORITY,
                    Dao.COLUMN_TASK_DATE
            };


            Cursor cursor = db.query(
                    Dao.TABLE_TASK ,   // The table to query
                    columns,           // The columns to return
                    null,         // The columns for the WHERE clause
                    null,     // The values for the WHERE clause
                    null,              // Don't group the rows
                    null,              // Don't filter by row groups
                    null               // The sort order
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Create a new Note object for each row
                    int taskID = cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DESC));
                    String priority = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_PRIORITY));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DATE));

                    TaskItem taskItem = new TaskItem();
                    taskItem.setTaskId(taskID);
                    taskItem.setTitle(title);
                    taskItem.setDescription(description);
                    taskItem.setPriority(priority);
                    taskItem.setCreatedOn(new Date(date)); // Assuming setCreatedOn accepts a String

                    // Add the Note object to the list
                    notes.add(taskItem);
                }
                cursor.close(); // Close the cursor after use
            }

            db.close(); // Close the database after use

            return Response.success(notes);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<List<TaskItem>, Failure> filterByTitle(String title) {
        try {
            List<TaskItem> tasks = new ArrayList<>();
            SQLiteDatabase db = taskDao.getReadableDatabase();

            // Define the query
            String[] columns = {
                    Dao.COLUMN_TASK_ID,
                    Dao.COLUMN_TASK_TITLE,
                    Dao.COLUMN_TASK_DESC,
                    Dao.COLUMN_TASK_PRIORITY,
                    Dao.COLUMN_TASK_DATE
            };

            String selection =  Dao.COLUMN_TASK_TITLE + " LIKE ?";
            String[] selectionArgs = {
                    "%" + title + "%" // Use LIKE to filter by title
            };

            Cursor cursor = db.query(
                    Dao.TABLE_TASK,   // The table to query
                    columns,           // The columns to return
                    selection,         // The columns for the WHERE clause
                    selectionArgs,     // The values for the WHERE clause
                    null,              // Don't group the rows
                    null,              // Don't filter by row groups
                    null               // The sort order
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Create a new Note object for each row
                    int taskID = cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_ID));
                    String titleFetched = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DESC));
                    String priority = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_PRIORITY));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DATE));

                    TaskItem taskItem = new TaskItem();
                    taskItem.setTaskId(taskID);
                    taskItem.setTitle(titleFetched);
                    taskItem.setDescription(description);
                    taskItem.setPriority(priority);
                    taskItem.setCreatedOn(new Date(date));

                    // Add the Note object to the list
                    tasks.add(taskItem);
                }
                cursor.close(); // Close the cursor after use
            }

            db.close(); // Close the database after use

            return Response.success(tasks);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<List<TaskItem>, Failure> filterByPriority(String prioritySearch) {
        try {
            List<TaskItem> tasks = new ArrayList<>();
            SQLiteDatabase db = taskDao.getReadableDatabase();

            // Define the query
            String[] columns = {
                    Dao.COLUMN_TASK_ID,
                    Dao.COLUMN_TASK_TITLE,
                    Dao.COLUMN_TASK_DESC,
                    Dao.COLUMN_TASK_PRIORITY,
                    Dao.COLUMN_TASK_DATE
            };

            String selection =  Dao.COLUMN_TASK_PRIORITY+ " LIKE ?";
            String[] selectionArgs = {
                    "%" + prioritySearch + "%" // Use LIKE to filter by title
            };

            Cursor cursor = db.query(
                    Dao.TABLE_TASK,   // The table to query
                    columns,           // The columns to return
                    selection,         // The columns for the WHERE clause
                    selectionArgs,     // The values for the WHERE clause
                    null,              // Don't group the rows
                    null,              // Don't filter by row groups
                    null               // The sort order
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Create a new Note object for each row
                    int taskID = cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_ID));
                    String titleFetched = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DESC));
                    String priority = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_PRIORITY));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_TASK_DATE));

                    TaskItem taskItem = new TaskItem();
                    taskItem.setTaskId(taskID);
                    taskItem.setTitle(titleFetched);
                    taskItem.setDescription(description);
                    taskItem.setPriority(priority);
                    taskItem.setCreatedOn(new Date(date));

                    // Add the Note object to the list
                    tasks.add(taskItem);
                }
                cursor.close(); // Close the cursor after use
            }

            db.close(); // Close the database after use

            return Response.success(tasks);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<Integer, Failure> deleteNote(int taskId) {
        try {
            SQLiteDatabase db = taskDao.getWritableDatabase();

            // Define 'where' part of query.
            String selection = Dao.COLUMN_TASK_ID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { String.valueOf(taskId) };

            // Issue SQL statement.
            int deletedRows = db.delete(Dao.TABLE_TASK, selection, selectionArgs);

            Log.d("DatabaseDebug", "Deleted rows: " + deletedRows);

            db.close();
            return Response.success(deletedRows);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }
}
