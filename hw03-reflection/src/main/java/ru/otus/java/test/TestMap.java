package ru.otus.java.test;

import ru.otus.java.annotations.After;
import ru.otus.java.annotations.Before;
import ru.otus.java.annotations.Test;
import ru.otus.java.asserts.Assert;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
    protected Map<Integer, String> testMap;

    @Before
    public void initMap() {
        testMap = new HashMap<>();
    }

    @After
    public void clearMap() {
        testMap.clear();
    }

    @Test
    public void putSingleValue() {
        testMap.put(1, "Java");

        Assert.assertTrue(testMap.size() == 1);
    }

    @Test
    public void putDifferentValueInSameKey() {
        String value1 = "Java";
        String value2 = "Value";
        testMap.put(1, value1);
        testMap.put(1, value2);
        // специально сделоно неправильное уловие, для проверки статистики fail тестов
        Assert.assertTrue(testMap.get(1).equals(value1));
    }

    public void withoutTest() {
        System.out.println("This method without test annotation");
    }
}
