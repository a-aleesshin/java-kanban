package ru.app.tasktracker.interfaces;

import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.Task;
import ru.app.tasktracker.task.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IManager {
    /**
     * Manager
     */
    public List<Task> getHistory();

    /**
     * Base task
     */
    public List<Task> getTasks();

    public void createTask(Task task);

    public void updateTask(Task task);

    public void deleteTask(int taskId);

    public Task getTask(int taskId);

    /**
     * Epic
     */
    public List<Epic> getEpics();

    public void createEpic(Epic epic);

    public void updateEpic(Epic Epic);

    public void deleteEpic(int epicId);

    public Epic getEpic(int epicId);

    public List<SubTask> getAllSubtasksByEpicId(int id);


    /**
     * SubTask
     */
    public List<SubTask> getSubTasks();

    public int createSubTask(SubTask subTask);

    public void updateSubTask(SubTask subTask);

    public void deleteSubTask(int subTaskId);

    public SubTask getSubTaskById(int id);
}
