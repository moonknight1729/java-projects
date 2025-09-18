package com.repository;
import java.util.List;
import com.models.Status;       
import com.models.Task;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.File;
import java.util.LinkedList;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TaskRepository implements IRepository<Task> {

    private List<Task> repository;
    private final ObjectMapper mapper;
    private final File file;
    // Implementation here
    public TaskRepository() {
        // Constructor implementation here
        this.repository = new LinkedList<>();
        this.file = new File("tasks.json");
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        verifyFile();
        loadTasks();
    }

    @Override
    public void add(Task task) {
        repository.add(task);
        saveTasks();
        // Save tasks to file or database
    }

    @Override
    public void update(Task task) {
        
        this.repository.stream()
                .filter(t -> t.getTaskId() == task.getTaskId())
                .forEach(t -> {
                    t.setDescription(task.getDescription());
                    t.setStatus(task.getStatus());
                    t.setUpdatedAt(task.getUpdatedAt());
                });
        saveTasks();

    }

    @Override
    public boolean delete(int taskId) {
        // Implementation here
        this.repository.removeIf(t -> t.getTaskId() == taskId);
        saveTasks();
        return false;
    }

    @Override
    public Task getById(int taskId) {
        // Implementation here
        return this.repository.stream()
                .filter(t -> t.getTaskId() == taskId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Task> getAll() {
        // Implementation here
        return this.repository;
    }

    @Override
    public List<Task> getByStatus(Status status) {
        // Implementation here
        return this.repository.stream()
                .filter(t -> t.getStatus().equals(status))
                .toList();
    }

    public void saveTasks() {
        
         try {
            mapper.writeValue(this.file, this.repository);
        } catch (JsonProcessingException e) {
           System.out.println("Error saving tasks to file. " + e.getMessage());
        } catch (IOException e) {
          System.out.println("Error saving tasks to file. " + e.getMessage());
        }   

    }
    public void loadTasks() {
        
        try {
            this.repository = mapper.readValue(this.file, new TypeReference<List<Task>>() {});
        } catch (JsonProcessingException e) {
            System.out.println("Error loading tasks from file. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading tasks from file. " + e.getMessage());
        }
    }
    public void verifyFile() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
