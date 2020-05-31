package ru.otus.java.memento;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.AtmImpl;

public class Memento {
    private final Atm atm;

    public Memento(Atm atm) {
        this.atm = new AtmImpl(atm);
    }

    public Atm getState() {
        return atm;
    }
}
