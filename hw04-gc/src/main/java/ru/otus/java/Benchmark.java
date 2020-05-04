package ru.otus.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Benchmark implements BenchmarkMBean {
    private Map<String, Person> personMap;
    private int size;
    private Integer counter;
    private Random randomAge = new Random(18);

    public Benchmark(Integer counter) {
        this.counter = counter;
    }

    void run() throws InterruptedException {
        personMap = new HashMap<>();
        for (int idx = 0; idx < counter; idx++) {
            fillMap(size);
            Thread.sleep(20);
            personMap.entrySet().removeIf(entry ->
                    (entry.getValue().getAge() % 2 == 0));
            Thread.sleep(20);
            personMap.values().stream()
                    .filter(person ->
                    person.getHobby().equals(Person.Hobby.getRandomHobby().toString()))
                    .forEach(person -> person.setHobby(Person.Hobby.getRandomHobby().toString()));
            System.out.println("personMap.size: " + personMap.size());
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    private void fillMap(Integer maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            UUID uuid = UUID.randomUUID();
            personMap.put(uuid.toString(),
                    new Person(Person.Name.getRandomName().toString(),
                            Person.Hobby.getRandomHobby().toString(), randomAge.nextInt(55)));
        }
    }
}
