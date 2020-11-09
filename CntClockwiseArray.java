package com.company;

/*
Требуется написать метод, принимающий на вход размеры двумерного массива и выводящий массив в виде
инкременированной цепочки чисел, идущих по спирали против часовой стрелки.

Примеры:

2х3
1 6
2 5
3 4

3х1
1 2 3

4х4
01 12 11 10
02 13 16 09
03 14 15 08
04 05 06 07

0х7
Ошибка!
 */

public class CntClockwiseArray {

    public static void cntClockwiseArray()
    {
        int rows = 3;
        int cols = 7;

        int inc = 1;

        int[][] spirArr = new int[rows][cols];

        // сначала заполняем периметр

        for (int i = 0; i < rows; i++)
        {
            spirArr[i][0] = inc; // вниз
            inc++;
        }

        for (int j = 1; j < cols; j++)
        {
            spirArr[rows - 1][j] = inc; // вправо
            inc++;

        }

        for (int i = rows - 2; i >= 0; i--)
        {
            if (spirArr[i][cols - 1] == 0) // страховка от повторного заполнения при cols = 1
            {
                spirArr[i][cols - 1] = inc; // вверх
                inc++;
            }
        }

        for (int j = cols - 2; j >= 1; j--)
        {
            if (spirArr[0][j] == 0) // страховка от повторного заполнения при rows = 1
            {
                spirArr[0][j] = inc; // влево
                inc++;
            }
        }

        // заполняем внутреннюю часть

        int x = 1;
        int y = 1;

        while (inc < rows * cols)
        {
            while (spirArr[x + 1][y] == 0) // вниз
            {
                spirArr[x][y] = inc;
                inc++;
                x++;
            }

            while (spirArr[x][y + 1] == 0) // вправо
            {
                spirArr[x][y] = inc;
                inc++;
                y++;
            }

            while (spirArr[x - 1][y] == 0) // вверх
            {
                spirArr[x][y] = inc;
                inc++;
                x--;
            }

            while (spirArr[x][y - 1] == 0) // влево
            {
                spirArr[x][y] = inc;
                inc++;
                y--;
            }
        }

        // вывод результата в консоль

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (spirArr[i][j] == 0) // убираем нолик в серединке
                {
                    spirArr[i][j] = inc;
                }

                if (spirArr[i][j] < 10) // добавляем нолик к одиночным цифрам
                {
                    System.out.print("0" + spirArr[i][j] + " ");
                }
                else {
                    System.out.print(spirArr[i][j] + " ");
                }
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {

        // отлавливаем ошибку массива

        try
        {
            cntClockwiseArray();

        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println("ОШИБКА! Неверно задан размер массива!");
        }
    }
}
