package gb.java_core.UI;

import gb.java_core.Functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static gb.java_core.Functions.*;

public class MainMenu extends Menu {

    public MainMenu() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(this::showTask1, "Задача 1"));
        menuItems.add(new MenuItem(this::showTask2, "Задача 2"));
        menuItems.add(new MenuItem(this::quit, "Выйти"));
    }

    public void quit() {
        ConsoleUi.close();
    }

    public void showTask1() throws IOException {
        ConsoleUi.println("Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup \n", Colors.MAGENTA);
        String sourcePath = ".\\src\\main\\java\\gb\\java_core\\";
        ArrayList<Object> listObjects = Functions.readDir(sourcePath);
        ArrayList<Object> onlyFilesList = Functions.sortOnlyFiles(listObjects);
        Functions.createBackup(onlyFilesList);
    }

    public void showTask2() {
        ConsoleUi.println("""
                Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой,
                например, состояния ячеек поля для игры в крестики нолики,
                где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом, 3 – резервное значение.
                Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
                Записать в файл 9 значений так, чтобы они заняли три байта.
                """, Colors.MAGENTA);
        int[] array = {1, 0, 4, 3, 2, 0, 2, 3, 2};
        ConsoleUi.println("Заданный массив из 9-ти элементов: " + Arrays.toString(array),Colors.YELLOW);
        functionRecordsInFileValues(array);
    }

}
