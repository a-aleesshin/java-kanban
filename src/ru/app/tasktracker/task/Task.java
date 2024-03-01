package ru.app.tasktracker.task;

import ru.app.tasktracker.enums.EStatus;
import ru.app.tasktracker.interfaces.ITask;
import ru.app.tasktracker.manager.BaseManager;

import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private EStatus status;
    private String description;

    public Task(String name, EStatus status, String description) {
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public EStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                status == task.status &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
