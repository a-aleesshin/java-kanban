package ru.app.tasktracker.interfaces;

import ru.app.tasktracker.task.Task;

import java.util.List;

public interface IHistoryManager {
    void add(Task task);

    List<Task> getHistory();
}
