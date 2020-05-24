package ru.otus.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.java.atm.AtmImpl;
import ru.otus.java.banknotes.Banknote;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
    }

    @Test
    void shouldAddAtm() {
        AtmImpl atm1 = new AtmImpl();
        atm1.addBanknotes(Banknote.TEN, 2);
        assertEquals(0, department.getAtmList().size());
        department.addAtm(atm1);
        assertEquals(1, department.getAtmList().size());
        assertEquals(atm1, department.getAtmList().get(0));
    }

    @Test
    void shouldGetBalanceFromAllAddAtm() {
        AtmImpl atm1 = new AtmImpl();
        AtmImpl atm2 = new AtmImpl();
        department.addAtm(atm1);
        department.addAtm(atm2);
        atm1.addBanknotes(Banknote.TEN, 2);
        atm2.addBanknotes(Banknote.TWO_THOUSAND, 5);
        assertEquals(atm1.getBalance() + atm2.getBalance(), department.getBalance());
    }

    @Test
    void shouldSaveStateOfAtm() {
        AtmImpl atm1 = new AtmImpl();
        department.addAtm(atm1);
        atm1.addBanknotes(Banknote.TEN, 2);
        assertNull(department.getAtmList().get(0).getMemento());
        department.saveState();
        assertNotNull(department.getAtmList().get(0).getMemento());
    }

    @Test
    void shouldResetToSavedStateOfAtm() {
        AtmImpl atm1 = new AtmImpl();
        atm1.addBanknotes(Banknote.TEN, 2);
        int balanceBeforeSave = atm1.getBalance();
        department.addAtm(atm1);
        department.saveState();
        atm1.addBanknotes(Banknote.TWO_THOUSAND, 5);
        department.resetToSavedState();
        assertEquals(balanceBeforeSave, atm1.getBalance());
        assertEquals(balanceBeforeSave, department.getBalance());
    }
}