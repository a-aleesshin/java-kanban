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
    public HashMap<Integer,Task> getTasks();
    public void createTask(Task task);
    public void updateTask(Integer taskId, Task task);
    public void  deleteTask(Integer taskId);
    public Task getTask(Integer taskId);

    /**
     * Epic
     */
    public HashMap<Integer,Epic> getEpics();
    public void createEpic(Epic epic);
    public void updateEpic(Integer epicId, Epic Epic);
    public void  deleteEpic(Integer epicId);
    public Epic getEpic(Integer epicId);
    public void calculateStatus(Epic epicSaved);


    /**
     * SubTask
     */
    public HashMap<Integer,SubTask> getSubTasks();
    public Integer createSubTask(SubTask subTask);
    public void updateSubTask(Integer subTaskId, SubTask subTask);
    public void  deleteSubTask(Integer subTaskId);
    public SubTask getSubTask(Integer subTaskId);
}
