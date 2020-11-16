package com.JohnnyGomezzz;

import School_test.Schoolboy;

public class Main {

    public static void main(String[] args) {

        int i = 1;

        while (i <= 25) {
            Schoolboy schoolboy = new Schoolboy();
            schoolboy.info();
            i++;
        }

    }
}
