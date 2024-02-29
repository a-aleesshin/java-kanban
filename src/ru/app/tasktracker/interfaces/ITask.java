package ru.app.tasktracker.interfaces;

import ru.app.tasktracker.enums.EStatus;

public interface ITask {
    public Integer getId();
    public String getName();
    public EStatus getStatus();
    public String getDescription();
}
