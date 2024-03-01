package ru.app.tasktracker;

import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.manager.BaseManager;
import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.SubTask;
import ru.app.tasktracker.task.Task;

public class App {
    public App() {
        this.startApp();
    }

    private void startApp() {
        System.out.println("Start App");
        System.out.println();

        BaseManager taskManager = new BaseManager();

        taskManager.createTask(new Task("Task 1", EStatus.NEW, "Desc task 1"));
        taskManager.createTask(new Task("Task 2", EStatus.NEW, "Desc task 2"));
        taskManager.createTask(new Task("Task 3", EStatus.NEW, "Desc task 3"));
        taskManager.createTask(new Task("Task 4", EStatus.NEW, "Desc task 4"));

        System.out.println(taskManager.getTasks());
        System.out.println();

        Epic firstEpic = new Epic("Epic 1", "Epic 1");
        taskManager.createEpic(firstEpic);

        Integer firstSubTak = taskManager.createSubTask(new SubTask("SubTask 1", EStatus.NEW, "SubTask task 1", firstEpic.getId()));
        Integer secondSubTak = taskManager.createSubTask(new SubTask("SubTask 2", EStatus.NEW, "SubTask task 2", firstEpic.getId()));
        Integer thirdSubTak = taskManager.createSubTask(new SubTask("SubTask 3", EStatus.NEW, "SubTask task 3", firstEpic.getId()));

        System.out.println(taskManager.getEpics());
        System.out.println();

        taskManager.getSubTaskById(firstSubTak).setStatus(EStatus.DONE);
        taskManager.updateSubTask(taskManager.getSubTaskById(firstSubTak));

        taskManager.getSubTaskById(secondSubTak).setStatus(EStatus.DONE);
        taskManager.updateSubTask(taskManager.getSubTaskById(secondSubTak));

        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpic(firstEpic.getId()));
        System.out.println();

        taskManager.getSubTaskById(thirdSubTak).setStatus(EStatus.DONE);
        taskManager.updateSubTask(taskManager.getSubTaskById(thirdSubTak));

        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpic(firstEpic.getId()));

        System.out.println();

        taskManager.getSubTaskById(firstSubTak).setStatus(EStatus.IN_PROGRESS);
        taskManager.updateSubTask(taskManager.getSubTaskById(firstSubTak));

        taskManager.getSubTaskById(secondSubTak).setStatus(EStatus.IN_PROGRESS);
        taskManager.updateSubTask(taskManager.getSubTaskById(secondSubTak));

        System.out.println("----------------------");
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpic(firstEpic.getId()));

        System.out.println("----------------------");

        taskManager.deleteSubTask(firstSubTak);
        taskManager.deleteSubTask(secondSubTak);

        System.out.println("====================");
        System.out.println(taskManager.getSubTasks());
        System.out.println(taskManager.getEpic(firstEpic.getId()));

        System.out.println("=======================");


        Epic secondEpic = new Epic("Epic 2", "Epic 2");
        taskManager.createEpic(secondEpic);

        taskManager.createSubTask(new SubTask("SubTask 4", EStatus.NEW, "SubTask task 4", secondEpic.getId()));
        taskManager.createSubTask(new SubTask("SubTask 5", EStatus.NEW, "SubTask task 5", secondEpic.getId()));
        taskManager.createSubTask(new SubTask("SubTask 6", EStatus.NEW, "SubTask task 6", secondEpic.getId()));
        System.out.println(taskManager.getEpics());
    }
}
