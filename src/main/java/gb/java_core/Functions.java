package gb.java_core;

import gb.java_core.UI.Colors;
import gb.java_core.UI.ConsoleUi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Functions {

    public static final String directoryPath = "./backup/";

    public static ArrayList<Object> readDir(String setWorkingPath) throws IOException {
        ArrayList<Object> my_list = new ArrayList<>();
        Path path = Path.of(setWorkingPath);
        DirectoryStream<Path> listObjects = Files.newDirectoryStream(path);
        for (Path item : listObjects) {
            my_list.add(item);
        }
        return my_list;
    }

    public static ArrayList<Object> sortOnlyFiles(ArrayList<Object> listObjects) {
        ArrayList<Object> onlyFilesList = new ArrayList<>();
        for (Object listObject : listObjects) {
            String item = listObject.toString();
            File file = new File(item);
            if (file.isFile()) {
                onlyFilesList.add(item);
            }
        }
        return onlyFilesList;
    }

    public static void createBackup(ArrayList<Object> onlyFilesList) throws IOException {
        Path path = Path.of(directoryPath);
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException | DirectoryNotEmptyException | NotDirectoryException |
                 NoSuchFileException e) {
            ConsoleUi.println("Каталог уже существует! " + e.getMessage(), Colors.RED);
            ConsoleUi.println("Произвожу удаление каталога...", Colors.YELLOW);
            File directory = new File(path.toString());
            deleteDirectory(directory);
            ConsoleUi.println("Каталог backup удалён!\n", Colors.GREEN);
            return;
        }
        for (Object o : onlyFilesList) {
            String sourcePath = o.toString();
            String[] fileName = sourcePath.replace("\\", "/").split("/");
            String destinationPath = directoryPath + fileName[fileName.length - 1];
            Path path1 = Path.of(sourcePath);
            Path path2 = Path.of(destinationPath);
            Files.copy(path1, path2);
        }
        listFilesInDirectory(directoryPath);
    }

    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }

    public static void listFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        ConsoleUi.println("Список файлов, созданных в папке backup: ", Colors.YELLOW);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    ConsoleUi.println(file.getName(), Colors.BLUE);
                } else if (file.isDirectory()) {
                    listFilesInDirectory(file.getAbsolutePath());
                }
            }
        } else {
            ConsoleUi.println("Каталог backup не существует!", Colors.RED);
        }
    }

    public static void functionRecordsInFileValues(int[] arr) {
        try {
            if (arr == null || arr.length != 9) {
                ConsoleUi.println("Поле должно быть массивом из 9 элементов!", Colors.RED);
            } else {
                OutputStream outputStream = new FileOutputStream("bin.txt");
                int i = 0;
                while (i < 3) {
                    byte b = 0;
                    int j = 0;
                    while (j < 3) {
                        b += arr[3 * i + j] << (j * 2);
                        j++;
                    }
                    outputStream.write(b);
                    i++;
                }
                outputStream.flush();
                outputStream.close();
                ConsoleUi.println("Файл bin.txt создан! В него записаны 9 значений представляющие 3 байта", Colors.GREEN);
            }
        } catch (IOException  e) {
            ConsoleUi.println("Произошла ошибка при записи в файл! " + e.getMessage(), Colors.RED);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            ConsoleUi.println("Каждый элемент должен быть в диапазоне [0, 3]!", Colors.RED);
        }
    }
}
