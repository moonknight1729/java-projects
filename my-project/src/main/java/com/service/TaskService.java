package com.service;
import com.repository.TaskRepository;
import com.models.Status;
import com.models.Task;
import java.util.List;

public class TaskService implements IService<Task> {

    private final TaskRepository repository;

    public TaskService() {
        this.repository = new TaskRepository();
    }

    @Override
    public void add(Task task) {
        this.repository.add(task);
    }
    @Override
    public void update(Task task) {
        this.repository.update(task);
    }

    @Override
    public boolean delete(int taskId) {
        return this.repository.delete(taskId);
    }

    @Override
    public Task getById(int taskId) {
        return this.repository.getById(taskId);
    }

    @Override
    public List<Task> getAll() {
        return this.repository.getAll();
    }

    @Override
    public List<Task> getByStatus(Status status) {
        return this.repository.getByStatus(status);
    }



}
