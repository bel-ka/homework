package ru.otus.java.atm;

import ru.otus.java.banknotes.Banknote;

import java.util.Map;

public interface Atm {
    int getBalance();

    Map<Banknote, Integer> getBanknotesCell();

    Map<Banknote, Integer> getBanknotes(int requiredAmount);

    void addBanknotes(Banknote banknote, Integer count);
}
