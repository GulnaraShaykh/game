package Games.src.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameInstaller {

    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        // Создание директорий
        String[] directories = {
                "Games/src", "Games/res", "Games/savegames", "Games/temp",
                "Games/src/main", "Games/src/test",
                "Games/res/drawables", "Games/res/vectors", "Games/res/icons"
        };

        for (String dir : directories) {
            File directory = new File(dir);
            boolean isCreated = directory.mkdirs(); // Используйте mkdirs() вместо mkdir(), чтобы создать все родительские директории
            log.append(isCreated ? "Создана директория " : "Не удалось создать директорию ").append(dir).append("\n");
        }

        // Создание файлов
        String[] files = {
                "Games/src/main/Main.java", "Games/src/main/Utils.java", "Games/temp/temp.txt"
        };

        for (String filePath : files) {
            try {
                File file = new File(filePath);
                boolean isFileCreated = file.createNewFile();
                log.append(isFileCreated ? "Создан файл " : "Не удалось создать файл ").append(filePath).append("\n");
            } catch (IOException e) {
                log.append("Ошибка при создании файла ").append(filePath).append(": ").append(e.getMessage()).append("\n");
            }
        }

        // Запись лога в файл temp.txt
        try (FileWriter writer = new FileWriter("Games/temp/temp.txt", false)) { // false указывает на перезапись файла, если он существует
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Ошибка при записи лога: " + e.getMessage());
        }

        // Вывод лога в консоль
        System.out.println(log);
    }
}