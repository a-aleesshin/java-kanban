package ru.app.tasktracker.manager;

import ru.app.tasktracker.interfaces.IHistoryManager;
import ru.app.tasktracker.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements IHistoryManager {

    private static final int MAX_HISTORY_TASK_SIZE = 10;  // Максимальный размер истории

    private final List<Task> historyTasks = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyTasks.size() >= MAX_HISTORY_TASK_SIZE) {
                historyTasks.remove(0);
                historyTasks.add(task);
            } else {
                historyTasks.add(task);
            }
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public List<Task> getHistory() {
        if (historyTasks == null) {
            System.out.println("GetHistory empty");
            return new ArrayList<>();
        }

        return new ArrayList<>(historyTasks);
    }
}
