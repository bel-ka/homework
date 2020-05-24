package ru.otus.java;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.AtmImpl;
import ru.otus.java.memento.Memento;
import ru.otus.java.memento.Originator;

import java.util.ArrayList;
import java.util.List;

public class Department implements Atm, Originator {
    private final List<AtmImpl> atmList = new ArrayList<>();

    public void addAtm(AtmImpl atm) {
        atmList.add(atm);
    }

    public List<AtmImpl> getAtmList() {
        return atmList;
    }

    @Override
    public int getBalance() {
        return atmList.stream().mapToInt(Atm::getBalance).sum();
    }

    @Override
    public void saveState() {
        atmList.forEach(AtmImpl::saveState);
    }

    @Override
    public void resetToSavedState() {
        atmList.forEach(AtmImpl::resetToSavedState);
    }
}
