package ru.app.tasktracker.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.interfaces.IManager;
import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.SubTask;
import ru.app.tasktracker.task.Task;
import ru.app.tasktracker.util.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private IManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testCreateTask() {
        Task task = new Task("Задача", EStatus.NEW,"Описание");
        taskManager.createTask(task);
        assertEquals(1, taskManager.getTasks().size(), "Неверное количество задач.");
        assertEquals(task, taskManager.getTask(task.getId()), "Задачи не совпадают.");
    }

    @Test
    void testCreateEpic() {
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createEpic(epic);
        assertEquals(1, taskManager.getEpics().size(), "Неверное количество эпиков.");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпики не совпадают.");
    }

    @Test
    void testCreateSubtask() {
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createEpic(epic);

        SubTask subtask = new SubTask("Подзадача", EStatus.NEW,"Описание", epic.getId());
        taskManager.createSubTask(subtask);

        assertEquals(1, taskManager.getSubTasks().size(), "Неверное количество подзадач.");
        assertEquals(subtask, taskManager.getSubTaskById(subtask.getId()), "Подзадачи не совпадают.");
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Задача", EStatus.NEW, "Описание");
        taskManager.createTask(task);

        task.setStatus(EStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        assertEquals(EStatus.IN_PROGRESS, taskManager.getTask(task.getId()).getStatus(), "Статус задачи не обновлен.");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Задача", EStatus.NEW, "Описание");
        taskManager.createTask(task);

        taskManager.deleteTask(task.getId());

        assertNull(taskManager.getTask(task.getId()), "Задача не удалена.");
    }


    @Test
    void testAddDifferentTaskTypes() {
        Task task = new Task("Задача 1", EStatus.NEW, "Описание 1");
        Epic epic = new Epic("Эпик 1", "Описание 1");
        SubTask subtask = new SubTask("Подзадача 1", EStatus.NEW, "Описание 1", 2);

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubTask(subtask);

        assertEquals(1, taskManager.getTasks().size(), "Неверное количество задач.");
        assertEquals(1, taskManager.getEpics().size(), "Неверное количество эпиков.");
        assertEquals(1, taskManager.getSubTasks().size(), "Неверное количество подзадач.");

        assertEquals(task, taskManager.getTask(task.getId()), "Задачи не совпадают.");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпики не совпадают.");
        assertEquals(subtask, taskManager.getSubTaskById(subtask.getId()), "Подзадачи не совпадают.");
    }

    @Test
    void testAddNewTask() {
        Task task = new Task("Задача добавлена", EStatus.NEW,"Описание");

        taskManager.createTask(task);

        Task savedTask = taskManager.getTask(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void testFindTaskById() {

        Task task = new Task("Задача", EStatus.NEW,"Описание задачи");
        taskManager.createTask(task);

        Task foundTask = taskManager.getTask(task.getId());

        assertNotNull(foundTask);
        assertEquals(task, foundTask);
    }

    @Test
    void testIdConflict() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task taskWithId = new Task("Задача 1", EStatus.NEW, "Явное указание id таска");
        taskWithId.setId(1);

        Task taskWithoutId = new Task("Задача 2", EStatus.NEW, "Описание 2");

        taskManager.createTask(taskWithId);
        taskManager.createTask(taskWithoutId);

        assertNotNull(taskManager.getTask(1));
        assertNotNull(taskManager.getTask(2));

        assertNotEquals(taskManager.getTask(1), taskManager.getTask(2));
    }
}