package services;

import domain.Task;
import domain.enums.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String jsonStored = Files.readString(jsonPath);
        int sequentialId = findMaxId(jsonStored) + 1;
        String json = "{\n" +
                " \"id\":" + sequentialId + "," +
                " \"description\": \"" + task.getDescription() + "\"," +
                " \"status\": \"" + task.getStatus() + "\"," +
                " \"createdAt\": \"" + task.getCreatedAt() + "\"," +
                " \"updatedAt\": \"" + task.getUpdatedAt() + "\"" +
                "}";
        //Verifica se o JSON não está vazio, caso não esteja adicionada uma , antes da classe formato json

        // Adiciona o json ao arquivo
        try {
            String jsonFile = Files.readString(jsonPath);
            if (!jsonFile.equals("[]")) {
                json = "," + json;
            }
            jsonFile = jsonFile.replace("]", "");
            jsonFile += json;
            jsonFile += "]";
            Files.writeString(jsonPath, jsonFile);
            System.out.println("Task added successfully");
        } catch (IOException e) {
            throw new IOException("Failed to write the json string in the specified json path");
        }
    }

    public static int findMaxId(String json) {
        int max = 0;
        Pattern pattern = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            try {
                int currentId = Integer.parseInt(matcher.group(1));
                if (currentId > max) {
                    max = currentId;
                }
            } catch (NumberFormatException e) {
                // Skip if the ID isn't a valid integer
            }
        }
        return max;
    }
}
