package ru.otus.java;

import java.util.Random;

public class Person {
    private String name;
    private String hobby;
    private Integer age;


    public Person(String name, String hobby, Integer age) {
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Integer getAge() {
        return age;
    }

    enum Name {
        Emma,
        William,
        James,
        Logan,
        Jacob,
        Amelia,
        Oliver,
        Ava,
        Noah,
        Sophia,
        Dorothy;

        public static Name getRandomName() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    enum Hobby {
        Fashion,
        Hacking,
        Knitting,
        Collecting,
        Sailing,
        Painting,
        Soccer,
        Yoga,
        Polo;

        public static Hobby getRandomHobby() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }
}
