package ru.app.tasktracker.util;

import org.junit.jupiter.api.Test;
import ru.app.tasktracker.interfaces.IHistoryManager;
import ru.app.tasktracker.interfaces.IManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void testGetDefaultTaskManager() {
        IManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
        assertNotNull(taskManager, "Менеджер задач пуст.");
    }

    @Test
    void testGetDefaultHistoryManager() {
        IHistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории пуст.");
    }
}