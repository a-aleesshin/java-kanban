package ru.app.tasktracker.interfaces;

import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.Task;
import ru.app.tasktracker.task.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

public interface IManager {
    /**
     * Base task
     */
    public ArrayList<Task> getTasks();
    public void createTask(Task task);
    public void updateTask(Task task);
    public void  deleteTask(int taskId);
    public Task getTask(int taskId);

    /**
     * Epic
     */
    public HashMap<Integer,Epic> getEpics();
    public void createEpic(Epic epic);
    public void updateEpic(Epic Epic);
    public void  deleteEpic(int epicId);
    public Epic getEpic(int epicId);
    public ArrayList<SubTask> getAllSubtasksByEpicId(int id);


    /**
     * SubTask
     */
    public HashMap<Integer,SubTask> getSubTasks();
    public int createSubTask(SubTask subTask);
    public void updateSubTask(SubTask subTask);
    public void  deleteSubTask(int subTaskId);
    public SubTask getSubTask(int subTaskId);
}
