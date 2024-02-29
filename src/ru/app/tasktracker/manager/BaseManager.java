package ru.app.tasktracker.manager;

import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.interfaces.IManager;
import ru.app.tasktracker.task.Epic;
import ru.app.tasktracker.task.SubTask;
import ru.app.tasktracker.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseManager implements IManager {
    private HashMap<Integer,Task> tasks;
    private HashMap<Integer,Epic> epics;
    private HashMap<Integer,SubTask> subTasks;
    private static Integer id = 0;

    public BaseManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private Integer generateId() {
        return ++id;
    }

    @Override
    public HashMap<Integer,Task> getTasks() {
        return this.tasks;
    }

    @Override
    public void createTask(Task task) {
        Integer newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
    }

    @Override
    public void updateTask(Integer taskId, Task task) {
        tasks.put(taskId, task);
    }

    @Override
    public void deleteTask(Integer taskId) {
        tasks.remove(taskId);
    }

    @Override
    public Task getTask(Integer taskId) {
        return tasks.get(taskId);
    }

    @Override
    public HashMap<Integer,Epic> getEpics() {
        return this.epics;
    }

    @Override
    public void createEpic(Epic epic) {
       epic.setId(generateId());
       epics.put(epic.getId(), epic);
       calculateStatus(epic);
    }

    @Override
    public void updateEpic(Integer epicId, Epic epic) {
        epics.put(epicId, epic);
    }

    @Override
    public void deleteEpic(Integer epicId) {
        epics.remove(epicId);
    }

    @Override
    public Epic getEpic(Integer epicId) {
        return epics.get(epicId);
    }

    @Override
    public void calculateStatus(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubtaskIds().size() == 0) {
                epic.setStatus(EStatus.NEW);
            } else {
                ArrayList<SubTask> subtasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;

                for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
                    subtasksNew.add(subTasks.get(epic.getSubtaskIds().get(i)));
                }

                for (SubTask subtask : subtasksNew) {
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
    public HashMap<Integer,SubTask> getSubTasks() {
        return this.subTasks;
    }

    public SubTask getSubTaskById(Integer id) {
        return subTasks.get(id);
    }

    @Override
    public Integer createSubTask(SubTask subTask) {
        Integer newSubtaskId = generateId();
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
    public void updateSubTask(Integer subTaskId, SubTask subTask) {
        if (subTasks.containsKey(subTaskId)) {
            SubTask subTaskUpdate = subTasks.get(subTaskId);
            subTasks.put(subTaskUpdate.getId(), subTaskUpdate);
            Epic epic = epics.get(subTaskUpdate.getEpicId());
            calculateStatus(epic);
        } else {
            System.out.println("Subtask not found");
        }
    }

    @Override
    public void deleteSubTask(Integer subTaskId) {
        SubTask subtask = subTasks.get(subTaskId);

        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove(subtask.getId());
            calculateStatus(epic);
            subTasks.remove(subtask.getId());
        } else {
            System.out.println("Subtask not found");
        }
    }

    @Override
    public SubTask getSubTask(Integer subTaskId) {
        return subTasks.get(subTaskId);
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
