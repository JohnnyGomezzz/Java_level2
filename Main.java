package com.company;

/*

1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных слов,
 из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.

2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер
телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
тогда при запросе такой фамилии должны выводиться все телефоны.

 */
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {

    public static void getWords()
    {
        // на входе имеем массив слов
        String[] words = {
            "table", "cup", "chair", "TV", "plate", "fork", "spoon", "fridge", "microwave oven", "dishwasher",
                "window", "cup", "cupboard", "plant", "fork", "spoon", "plate", "fork", "chair"
        };

        // создаем коллекцию
        Set<String> kitchen = new LinkedHashSet<>();

        // счётчик
        int counter = 0;

        // копируем массив в коллекцию
        Collections.addAll(kitchen, words);

        // выясняем, сколько раз в массиве встречается каждое слово
        for (String kitchenStuff : kitchen)
        {
            for (String stuff : words)
            {
                if (kitchenStuff.equals(stuff))
                {
                    counter++;
                }
            }

            // выводим в консоль список слов с количеством повторений
            System.out.println(kitchenStuff + " x" + counter);
            counter = 0;
        }

    }

    public static void main(String[] args) {

        // задание 1
        System.out.println("Задание 1\n");

        getWords();

        // задание 2
        System.out.println("\nЗадание 2\n");

        PhoneBook phoneBook = new PhoneBook();

        phoneBook.add(9995566, "Ivanov");
        phoneBook.add(9995567, "Ivanov");
        phoneBook.add(9995568, "Ivanov");
        phoneBook.add(9994590, "Petrov");
        phoneBook.add(9991289, "Sidorov");
        phoneBook.add(9995678, "Smirnov");

        System.out.println(phoneBook.contacts + "\n");

        phoneBook.get("Ivanov");
        phoneBook.get("Petrov");
        phoneBook.get("Smirnov");
        phoneBook.get("Sidorov");
    }
}
