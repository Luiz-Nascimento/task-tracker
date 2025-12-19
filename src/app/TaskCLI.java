package app;

import services.TaskService;
import domain.Task;
import domain.enums.TaskStatus;

import java.io.IOException;
import java.time.LocalDateTime;

public class TaskCLI {

    static void main(String[] args) throws IOException {
        
        final TaskService taskService = new TaskService();
        System.out.println("Task-Tracker CLI started...");
        if (args.length > 1) {
            if (args[0].equals("add")) {
                System.out.println("Adding a new task...");
                Long id = 1L;
                String description = args[1];
                TaskStatus status = TaskStatus.TODO;
                LocalDateTime createdAt = LocalDateTime.now();
                LocalDateTime updatedAt = LocalDateTime.now();
                Task task = new Task(id, description, status, createdAt, updatedAt);
                taskService.addToJson(task);
            }
        }
        System.out.println("Task-Tracker CLI closed.");
    }
}
