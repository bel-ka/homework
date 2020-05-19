package ru.otus.java.banknotes;

public class OneHundredNominal implements Banknotes {
    private int nominal = 100;
    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return "OneHundredNominal{" +
                "nominal=" + nominal +
                '}';
    }
}
