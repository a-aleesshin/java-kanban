package ru.app.tasktracker.manager;

import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.interfaces.IHistoryManager;
import ru.app.tasktracker.interfaces.IManager;
import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.SubTask;
import ru.app.tasktracker.task.Task;
import ru.app.tasktracker.util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements IManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private final IHistoryManager historyManager;
    private int id = 0;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    private int generateId() {
        return ++id;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return new ArrayList<>();
        }

        return new ArrayList<>(tasks.values());
    }

    @Override
    public void createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public void deleteTask(int taskId) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public Task getTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Epic> getEpics() {
        if (epics.size() == 0) {
            System.out.println("Epic list is empty");
            return new ArrayList<>();
        }

        return new ArrayList<>(epics.values());
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic updateEpic = epics.get(epic.getId());
            updateEpic.setName(epic.getName());
            updateEpic.setDescription(epic.getDescription());
        } else {
            System.out.println("Epic not found");
        }
    }

    @Override
    public void deleteEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.clearSubtasks();
            epics.remove(id);
        } else {
            System.out.println("Epic not found");
        }
    }

    @Override
    public Epic getEpic(int epicId) {
        historyManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    private void calculateStatus(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubtaskIds().size() == 0) {
                epic.setStatus(EStatus.NEW);
            } else {
                int countDone = 0;
                int countNew = 0;

                for (int subtaskId : epic.getSubtaskIds()) {
                    SubTask subtask = subTasks.get(subtaskId);

                    if (subtask.getStatus() == EStatus.DONE) {
                        countDone++;
                    }
                    if (subtask.getStatus() == EStatus.NEW) {
                        countNew++;
                    }
                    if (subtask.getStatus() == EStatus.IN_PROGRESS) {
                        epic.setStatus(EStatus.IN_PROGRESS);
                        return;
                    }
                }

                if (countDone == epic.getSubtaskIds().size()) {
                    epic.setStatus(EStatus.DONE);
                } else if (countNew == epic.getSubtaskIds().size()) {
                    epic.setStatus(EStatus.NEW);
                } else {
                    epic.setStatus(EStatus.IN_PROGRESS);
                }
            }
        } else {
            System.out.println("Epic not found");
        }
    }

    @Override
    public List<SubTask> getSubTasks() {
        if (subTasks.size() == 0) {
            System.out.println("Subtasks list is empty");
            return new ArrayList<>();
        }
        return new ArrayList<>(subTasks.values());
    }

    public SubTask getSubTaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public int createSubTask(SubTask subTask) {
        int newSubtaskId = generateId();
        subTask.setId(newSubtaskId);

        Epic epic = epics.get(subTask.getEpicId());

        if (epic != null) {
            subTasks.put(newSubtaskId, subTask);
            epic.addSubTaskId(newSubtaskId);
            calculateStatus(epic);

            return newSubtaskId;
        } else {
            System.out.println("Epic not found");
            return -1;
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            SubTask subTaskUpdate = subTasks.get(subTask.getId());
            subTasks.put(subTaskUpdate.getId(), subTaskUpdate);
            Epic epic = epics.get(subTaskUpdate.getEpicId());
            calculateStatus(epic);
        } else {
            System.out.println("Subtask not found");
        }
    }

    @Override
    public void deleteSubTask(int subTaskId) {
        SubTask subtask = subTasks.get(subTaskId);

        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.deleteSubtask(subtask.getId());
            calculateStatus(epic);
            subTasks.remove(subtask.getId());
        } else {
            System.out.println("Subtask not found");
        }
    }

    @Override
    public List<SubTask> getAllSubtasksByEpicId(int id) {
        if (epics.containsKey(id)) {
            ArrayList<SubTask> subtasksNew = new ArrayList<>();
            Epic epic = epics.get(id);

            for (int subtaskId : epic.getSubtaskIds()) {
                subtasksNew.add(subTasks.get(subtaskId));
            }

            return subtasksNew;
        } else {
            System.out.println("Subtask not found");
            return new ArrayList<>();
        }
    }


    public void printTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return;
        }

        for (Task task : tasks.values()) {
            System.out.println("Task{" +
                    "description='" + task.getDescription() + '\'' +
                    ", id=" + task.getId() +
                    ", name='" + task.getName() + '\'' +
                    ", status=" + task.getStatus() +
                    '}');
        }
    }

    public void printEpics() {
        if (epics.size() == 0) {
            System.out.println("Epic list is empty");
            return;
        }
        for (Epic epic : epics.values()) {
            System.out.println("Epic{" +
                    "subtasksIds=" + epic.getSubtaskIds() +
                    ", description='" + epic.getDescription() + '\'' +
                    ", id=" + epic.getId() +
                    ", name='" + epic.getName() + '\'' +
                    ", status=" + epic.getStatus() +
                    '}');
        }
    }

    public void printSubtasks() {
        if (subTasks.size() == 0) {
            System.out.println("Subtask list is empty");
            return;
        }
        for (SubTask subTask : subTasks.values()) {
            System.out.println("Subtask{" +
                    "epicId=" + subTask.getEpicId() +
                    ", description='" + subTask.getDescription() + '\'' +
                    ", id=" + subTask.getId() +
                    ", name='" + subTask.getName() + '\'' +
                    ", status=" + subTask.getStatus() +
                    '}');
        }
    }
}
