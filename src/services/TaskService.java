package services;

import domain.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskService {

    public void addToJson(Task task) throws IOException {
        Path jsonPath = Path.of("/home/luiz/Documents/tasks.json");

        //Inicializa o arquivo JSON caso não esteja inicializado com []
        if (!Files.exists(jsonPath)) {
            String templateJson = "[]";
            try {
                Files.writeString(jsonPath, templateJson);
            } catch (IOException e) {
                throw new IOException("Error while initializing JSON with the template string");
            }
        }
        // Transforma a classe no formato JSON
        String json = "{\n" +
                " \"id\":" + task.getId() + "," +
                " \"description\": \"" + task.getDescription() + "\"," +
                " \"status\": \"" + task.getStatus() + "\"," +
                " \"createdAt\": \"" + task.getCreatedAt() + "\"," +
                " \"updatedAt\": \"" + task.getUpdatedAt() + "\"" +
                "}";
        //Verifica se o JSON não está vazio, caso não esteja adicionada uma , antes da classe formato json
        try {
            String jsonFile = Files.readString(jsonPath);
            if (!jsonFile.equals("[]")) {
                json = "," + json;
            }
        } catch (IOException e) {
            throw new IOException("Failed to read the json string in the specified json path");
        }
        try {
            String newJson = Files.readString(jsonPath);
            newJson = newJson.replace("]", "");
            newJson += json;
            newJson += "]";
            Files.writeString(jsonPath, newJson);
            System.out.println("Task added successfully");
        } catch (IOException e) {
            throw new IOException("Failed to write the json string in the specified json path");
        }
    }
}
