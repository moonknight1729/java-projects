package com.example;
import java.util.Scanner;
import java.util.Arrays;
import com.models.Task;
import com.service.TaskService;
import com.models.Status;
import java.util.List;


public class App 
{

    private static final Scanner sc = new Scanner(System.in);
    private final static TaskService taskService = new TaskService();
    public static void main( String[] args )
    {
          String line = "";
        System.out.println("Task Tracker CLI");

        while (!line.equals("exit")) {
            System.out.print("Enter a command: ");
            line = sc.nextLine();
            if (line.replace(" ", "").trim().equalsIgnoreCase("exit")) break;

            String[] arguments = line.split(" ");
            switch (arguments.length) {
                case 0 : processEmptyArguments(arguments);
                break;
                case 1 : processSingleArgument(arguments);
                break;
                default : processMultipleArguments(arguments);
            }
        }

        System.out.println("Exiting...");
    }

public static void processEmptyArguments(String[] args){
        System.out.println("No command provided");
    }

    public static void processSingleArgument(String[] args){
        switch(args[0]){
            case "help":
               processHelpArgument(args);
                break;
            case "list":
               processListAllArgument(args);
                break;
        }
    }

    public static void processMultipleArguments(String[] args){
        switch(args[0]){
            case "add":
                processAddArgument(args);
                break;
            case "update":
                processUpdateArgument(args);
                break;
            case "delete":
                processDeleteArgument(args);
                break;
            case "mark-in-progress":
                processMarkInProgressArgument(args);
                break;
            case "mark-done":
                processMarkDoneArgument(args);
                break;
            case "list":
                processListArgument(args);
                break;    
        }
    }

    public static void processAddArgument(String[] args){


        Task task = new Task(args[1], Status.TODO);
        taskService.add(task);
        System.out.println("Task added successfully (ID: " + task.getTaskId() + ")");
    
    }

    public static void processUpdateArgument(String[] args){

        int id = -1;
        try{
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
       
        Task task = taskService.getById(id);
        if(task != null){
            task.setDescription(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
            task.setUpdatedAt(java.time.LocalDate.now());
            taskService.update(task);
            System.out.println("Task with id " + id + " updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    
    }

    public static void processDeleteArgument(String[] args){

        int id = -1;
        try{
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        boolean isDeleted = taskService.delete(id);
        if(isDeleted){
            System.out.println("Task with id " + id + " deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    
    }

    public static void processMarkInProgressArgument(String[] args){

        int id = -1;
        try{
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        Task task = taskService.getById(id);
        if(task != null){
            task.setStatus(Status.IN_PROGRESS);
            task.setUpdatedAt(java.time.LocalDate.now());
            taskService.update(task);
            System.out.println("Task with id " + id + " is now in progress.");
        } else {
            System.out.println("Task not found.");
        }
    
    }

    public static void processMarkDoneArgument(String[] args){

        int id = -1;
        try{
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
            return;
        }
        Task task = taskService.getById(id);
        if(task != null){
            task.setStatus(Status.DONE);
            task.setUpdatedAt(java.time.LocalDate.now());
            taskService.update(task);
            System.out.println("Task with id " + id + " is now done.");
        } else {
            System.out.println("Task not found.");
        }

    }

    public static void processListArgument(String[] args){

        if(args.length == 2){
            Status status = null;
            try{
                status = Status.valueOf(args[1].toUpperCase());
            } catch(IllegalArgumentException e){
                System.out.println("Invalid status. Use 'help' to display the available commands.");
                return;
            }
            List<Task> tasks = taskService.getByStatus(status);
            if(tasks.isEmpty()){
                System.out.println("No tasks found with status " + status);
            } else {
                tasks.forEach(System.out::println);
            }
        } else {
            System.out.println("Invalid command. Use 'help' to display the available commands.");
        }
    
    }

    public static void processListAllArgument(String[] args){

        List<Task> tasks = taskService.getAll();
        if(tasks.isEmpty()){
            System.out.println("No tasks found.");
        } else {
            tasks.forEach(System.out::println);
        }
    
    }
    public static void processHelpArgument(String[] args){
        System.out.println("Available commands:");
        System.out.println("1. add <description> - Add a new task with the given description.");
        System.out.println("2. update <id> <new description> - Update the description of the task with the given id.");
        System.out.println("3. delete <id> - Delete the task with the given id.");
        System.out.println("4. mark-in-progress <id> - Mark the task with the given id as in progress.");
        System.out.println("5. mark-done <id> - Mark the task with the given id as done.");
        System.out.println("6. list - List all tasks.");
        System.out.println("7. list <status> - List tasks with the given status (TODO, IN_PROGRESS, DONE).");
        System.out.println("8. help - Display this help message.");
        System.out.println("9. exit - Exit the application.");
    }

}