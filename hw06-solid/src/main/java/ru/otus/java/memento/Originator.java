package ru.otus.java.memento;

import ru.otus.java.atm.Atm;
import ru.otus.java.exception.AtmException;

public class Originator {
    private Memento memento;

    public Originator() {
    }

    public void saveState(Atm atm) {
        memento = new Memento(atm);
    }

    public void resetToSavedState(Atm atm) {
        if (memento == null) {
            throw new AtmException("Отсутствует ранее сохраненное состояние банкомата.");
        } else {
            atm.getBanknotesCell().clear();
            atm.getBanknotesCell().putAll(memento.getState().getBanknotesCell());
        }
    }
}
