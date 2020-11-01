package com.company;

public class MyArrayDataException extends Exception
{
    public MyArrayDataException(int col, int row)
    {
        System.err.println("Ошибка! В ячейке [" + col + "][" + row + "] не число");
    }
}
