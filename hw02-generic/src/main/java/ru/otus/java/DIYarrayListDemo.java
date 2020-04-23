package ru.otus.java;

import java.util.Collections;
import java.util.List;

public class DIYarrayListDemo {
    public static void main(String[] args) {
        List<Integer> list = new DIYarrayList<>();
        Collections.addAll(list, 1, 3, 4, 45, 2, 4, 6, 33, 46, 23, 56, 75, 22, 21, 34, 58,
                24, 4566, 32, 435, 7, 214, 86);
        System.out.print("DIYarrayList1: ");
        list.forEach(x -> System.out.print(" [" + x + "] "));

        List<Object> listCopy = new DIYarrayList<>();
        Collections.addAll(listCopy, "w", "sd", "a", "b", "c", "d", "e", 33.4, 46, "p", "f", "h",
                22.6, 21.1, 34.4, 58.7, "o", "a", "s", "u", 7, "sds", "r");
        System.out.print( "\nDIYarrayList2 before copy: ");
        listCopy.forEach( x -> System.out.print(" [" + x + "] "));

        Collections.copy(listCopy, list);
        System.out.print( "\nDIYarrayList2 after copy: ");
        listCopy.forEach( x -> System.out.print(" [" + x + "] "));

        Collections.sort(list);
        System.out.print("\nsort DIYarrayList2: ");
        list.forEach( x -> System.out.print(" [" + x + "] "));
    }
}
