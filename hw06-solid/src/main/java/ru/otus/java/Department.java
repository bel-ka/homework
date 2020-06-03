package ru.otus.java;

import ru.otus.java.atm.Atm;
import ru.otus.java.atm.AtmImpl;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<AtmImpl> atmList = new ArrayList<>();

    public void addAtm(AtmImpl atm) {
        atmList.add(atm);
    }

    public List<AtmImpl> getAtmList() {
        return atmList;
    }

    public int getBalanceOfAllAtm() {
        return atmList.stream().mapToInt(Atm::getBalance).sum();
    }

    public void saveStateOfAllAtm() {
        atmList.forEach(AtmImpl::saveState);
    }

    public void resetStateOfAllAtm() {
        atmList.forEach(AtmImpl::resetToSavedState);
    }
}
