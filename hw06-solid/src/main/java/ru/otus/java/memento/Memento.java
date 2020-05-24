package ru.otus.java.memento;

import ru.otus.java.atm.AtmImpl;

public class Memento {
    private AtmImpl atm;

    public Memento(AtmImpl atm) {
        this.atm = new AtmImpl(atm);
    }

    public AtmImpl getState() {
        return atm;
    }
}
