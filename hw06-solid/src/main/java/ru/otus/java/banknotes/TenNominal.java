package ru.otus.java.banknotes;

public class TenNominal implements Banknotes {
    private int nominal = 10;
    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return "TenNominal{" +
                "nominal=" + nominal +
                '}';
    }
}
