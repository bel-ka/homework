package ru.otus.java.banknotes;

public class TwoThousandNominal implements Banknotes{
    private int nominal = 2000;
    @Override
    public int getNominal() {
        return nominal;
    }

    @Override
    public String toString() {
        return "TwoThousandNominal{" +
                "nominal=" + nominal +
                '}';
    }
}
