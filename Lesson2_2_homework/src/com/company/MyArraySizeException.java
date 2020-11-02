package com.company;

public class MyArraySizeException extends Exception
{
    public MyArraySizeException()
    {
        System.err.println("Некорректный ввод массива, не 4х4");
    }
}
