package School_test;

import java.util.Random;

public class Schoolboy {

    private final String[] boyName = {
            "Алексей", "Александр", "Антон", "Андрей", "Борис", "Владимир", "Виктор", "Вячеслав",
            "Валентин", "Георгий", "Гавриил", "Григорий", "Дмитрий", "Денис", "Евгений", "Егор",
            "Захар", "Иван", "Ираклий", "Константин", "Леонид", "Максим", "Николай", "Олег", "Павел", "Пётр",
            "Роман", "Руслан", "Станислав", "Степан", "Тимофей", "Фёдор", "Харитон", "Эдуард", "Яков", "Ярослав"
    };

    private final String[] boySurname = {
            "Алексеев", "Александров", "Антонов", "Андреев", "Борисов", "Гергиев", "Гаврилов",
            "Григорьев", "Дмитриев", "Денисов", "Ежов", "Ершов", "Егоров",
            "Захаров", "Иванов", "Ирискин", "Константинов", "Леонидов", "Максимов", "Николаев", "Орехов", "Павлов",
            "Петров", "Романов", "Русов", "Станицын", "Степанов", "Тимофеев", "Фёдоров", "Харитонов", "Эвклидов",
            "Яковлев", "Ярков"
    };

    Random random = new Random();

    private int randomName = random.nextInt(boyName.length);
    private int randomSurname = random.nextInt(boySurname.length);

    private double boyAverageGrade = 3 + 2 * random.nextDouble();

    private String name = boyName[randomName];
    private String surname = boySurname[randomSurname];
    private int age = 7;
    private double averageGrade = boyAverageGrade;
    
    public Schoolboy()
    {

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void info()
    {
        System.out.printf(
                name + " " + surname + ", " + age + " лет, средняя оценка: " + "%.1f\n", averageGrade
        );
    }




}
