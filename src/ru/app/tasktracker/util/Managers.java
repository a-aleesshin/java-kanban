package ru.app.tasktracker.util;

import ru.app.tasktracker.interfaces.IHistoryManager;
import ru.app.tasktracker.interfaces.IManager;
import ru.app.tasktracker.manager.InMemoryHistoryManager;
import ru.app.tasktracker.manager.InMemoryTaskManager;

public class Managers {
    public static IManager getInMemoryTaskManager(IHistoryManager historyManager) {
        return new InMemoryTaskManager();
    }

    public static IHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
