package com.repository;
import java.util.List;
import com.models.Status;

public interface IRepository<Task> {


    void add(Task task);
    void update(Task task);
    boolean delete(int taskId);
    Task getById(int taskId);
    List<Task> getAll();
    List<Task> getByStatus(Status status);




    
}
