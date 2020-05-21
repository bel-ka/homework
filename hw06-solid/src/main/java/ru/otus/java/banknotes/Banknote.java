package ru.otus.java.banknotes;

public enum Banknote {
    TEN(10),
    ONE_HUNDRED(100),
    TWO_THOUSAND(2000);

    private int nominal;

    Banknote(int nominal){
        this.nominal = nominal;
    }
    public int getNominal(){ return nominal;}
}
