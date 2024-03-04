package ru.app.tasktracker.task;

import org.junit.jupiter.api.Test;
import ru.app.tasktracker.enums.EStatus;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void testEpicEqualsById() {
        Epic epic1 = new Epic("Эпик", "Описание");
        Epic epic2 = new Epic("Эпик", "Описание");

        epic1.setId(3);
        epic2.setId(3);

        assertEquals(epic1, epic2);
    }

    @Test
    void testAddSubtaskToEpicSelf() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        epic.setId(1);

        SubTask subtask = new SubTask("Подзадача 1", EStatus.NEW,"Описание подзадачи 1", epic.getId());
        subtask.setId(2);
        epic.addSubTaskId(subtask.getId());

        assertNotEquals(epic, subtask);
    }

    @Test
    void testEpicAddSubtask() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        SubTask subtask = new SubTask("Подзадача", EStatus.NEW,"Описание подзадачи", epic.getId());
        epic.addSubTaskId(subtask.getId());
        assertTrue(epic.getSubtaskIds().contains(subtask.getId()));
    }

}