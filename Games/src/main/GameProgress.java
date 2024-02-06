package Games.src.main;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    // serialVersionUID используется для проверки совместимости сериализованных данных
    // с версией класса при десериализации
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    // Геттеры и сеттеры (при необходимости) могут быть добавлены для доступа к полям класса

    @Override
    public String toString() {
        // Переопределение метода toString() для удобного представления состояния объекта
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(100, 10, 1, 0.0);
        GameProgress game2 = new GameProgress(90, 14, 2, 42.5);
        GameProgress game3 = new GameProgress(75, 8, 4, 88.8);

        saveGame("Games/savegames/save1.dat", game1);
        saveGame("Games/savegames/save2.dat", game2);
        saveGame("Games/savegames/save3.dat", game3);


        List<String> saves = Arrays.asList(
                "Games/savegames/save1.dat",
                "Games/savegames/save2.dat",
                "Games/savegames/save3.dat"
        );

        zipFiles("Games/savegames/saves.zip", saves);

        for (String filePath : saves) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println(filePath + " was deleted.");
            } else {
                System.out.println("Cannot delete " + filePath);
            }
        }


    }

    public static void saveGame(String path, GameProgress gameProgress) {
        // Используйте try-with-resources для автоматического закрытия ресурсов
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println("An exception occurred during the save process: " + e.getMessage());
        }
    }


    public static void zipFiles(String zipPath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : files) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    File file = new File(filePath);
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("An exception occurred while reading the save file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("An exception occurred during the archiving process: " + e.getMessage());
        }
    }


}
