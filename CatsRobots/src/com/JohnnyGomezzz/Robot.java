package com.JohnnyGomezzz;

public class Robot implements Running, Jumping {

    private String name;
    private int runLength;
    private double jumpHeight;

    public Robot(String name, int runLength, double jumpHeight)
    {
        this.name = name;
        this.runLength = runLength;
        this.jumpHeight = jumpHeight;
    }

    public int getRunLength()
    {
        return runLength;
    }

    public double getJumpHeight()
    {
        return jumpHeight;
    }

    @Override
    public boolean run(Object obstacles)
    {
        if (runLength >= ((Track)obstacles).getLength())
        {
            System.out.println(name + " пробежал " + ((Track)obstacles).getLength() + " метров");
            return true;
        }

        else
        {
            System.out.println(name + " не смог пробежать " + ((Track)obstacles).getLength() + " метров и выбывает");
        }
        return false;
    }

    @Override
    public boolean jump(Object obstacles)
    {
        if (jumpHeight >= ((Wall)obstacles).getHeight())
        {
            System.out.println(name + " перепрыгнул препятствие высотой " + ((Wall)obstacles).getHeight() + " метра");
            return true;
        }

        else
        {
            System.out.println(name + " не смог перепрыгнуть препятствие высотой " + ((Wall)obstacles).getHeight()
                    + " метра и выбывает");
        }
        return false;
    }

}
