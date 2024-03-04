package ru.app.tasktracker.manager;

import org.junit.jupiter.api.Test;
import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void testAdd() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Задача", EStatus.NEW,"Описание");

        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history);
        assertEquals(1, history.size());

        assertEquals(task.getName(), history.get(0).getName());
        assertEquals(task.getDescription(), history.get(0).getDescription());
        assertEquals(task.getStatus(), history.get(0).getStatus());
    }

    @Test
    void testGetHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Задача 1", EStatus.NEW, "Описание задачи 1");
        Task task2 = new Task("Задача 2", EStatus.IN_PROGRESS, "Описание задачи 2");

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();

        assertNotNull(history);
        assertEquals(2, history.size());

        assertEquals(task1.getName(), history.get(0).getName());
        assertEquals(task1.getDescription(), history.get(0).getDescription());
        assertEquals(task1.getStatus(), history.get(0).getStatus());

        assertEquals(task2.getName(), history.get(1).getName());
        assertEquals(task2.getDescription(), history.get(1).getDescription());
        assertEquals(task2.getStatus(), history.get(1).getStatus());
    }
}