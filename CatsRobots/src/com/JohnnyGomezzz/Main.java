package com.JohnnyGomezzz;

public class Main {

    public static void main(String[] args) {

        Object[] participants = {                                       // массив Участники
                new Human("Олег",800, 0.6),
                new Human("Егор",1300, 0.8),
                new Robot("Вертер", 1500, 0.5),
                new Robot("C3P0", 800, 0.3),
                new Cat("Мурзик", 500, 1.5),
                new Cat("Барсик", 1000, 2)
        };

        Object[] obstacles = {                                       // массив Препятствия
                new Track(500),
                new Wall(0.5),
                new Track(1000),
                new Wall(0.75),
                new Track(1500),
                new Wall(1)
        };

        for(Object participant : participants)                        // участники "проходят" препятствия
        {
            for (Object obstacle : obstacles)
            {
                if (obstacle instanceof Track)
                {
                    if (!((Running)participant).run(obstacle)) break;
                }
                else {
                    if (!((Jumping) participant).jump(obstacle)) break;
                }
            }
        }
    }
}
