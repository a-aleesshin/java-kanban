package ru.app.tasktracker.task;

import org.junit.jupiter.api.Test;
import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.interfaces.IManager;
import ru.app.tasktracker.manager.InMemoryTaskManager;
import ru.app.tasktracker.util.Managers;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    @Test
    void testSubtaskEqualsById() {
        SubTask subtask1 = new SubTask("Подзадача", EStatus.NEW, "Описание", 1);
        subtask1.setId(2);
        SubTask subtask2 = new SubTask("Подзадача", EStatus.NEW, "Описание", 1);
        subtask2.setId(2);

        assertEquals(subtask1, subtask2);
    }

    @Test
    void testSubtaskCannotBeItsOwnEpic() {
        SubTask subtask = new SubTask("Подзадача 1", EStatus.NEW, "Описание", 1);
        subtask.setId(1);

        Epic epic = new Epic("Эпик 1", "Описание");
        epic.setId(1);
        epic.addSubTaskId(subtask.getId());

        assertNotEquals(epic, subtask);
    }
}