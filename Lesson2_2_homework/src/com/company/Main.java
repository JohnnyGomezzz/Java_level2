package com.company;
/*
1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4, при подаче массива другого
размера необходимо бросить исключение MyArraySizeException.

2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
должно быть брошено исключение MyArrayDataException, с детализацией в какой именно ячейке лежат неверные данные.

3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException
и MyArrayDataException, и вывести результат расчета.
*/

import java.util.Random;

public class Main {

    public static void setArray() throws MyArraySizeException, MyArrayDataException {

        int rows = 7; // задаём количество колонок и столбцов
        int cols = 4;

        Random random = new Random();

        String[][] array = new String[rows][cols];

        String[] randomArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Z"}; // массив для "рандома"

        int sum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int a = random.nextInt(randomArray.length); // заполняем массив рандомными стрингами из массива выше

                array[i][j] = randomArray[a];

                System.out.print(array[i][j] + " "); // выводим массив в консоль
            }
            System.out.println();
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                try {
                    sum += Integer.parseInt(array[i][j]); // преобразовываем стринги в целочисленные и суммируем

                } catch (NumberFormatException e)
                {
                    throw new MyArrayDataException(i, j); // вычисляем оставшиеся стринги и их координаты
                }
                finally {
                    continue;  // без него выводит только одну ошибку...
                }
            }
        }

        System.out.println("Сумма целочисленных элементов массива: " + sum);

        if (rows != 4 || cols != 4)
        {
            throw new MyArraySizeException(); // проверка размеров массива
        }
    }

    public static void main(String[] args) {

        try {
          setArray();  // запуск массива и обработка исключений
        }
        catch (MyArraySizeException | MyArrayDataException e)
        {

        }

    }
}
