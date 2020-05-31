package ru.otus.java;

import ru.otus.java.atm.AtmImpl;
import ru.otus.java.banknotes.Banknote;

public class DemoAtm {
    public static void main(String[] args) {
        AtmImpl atm1 = new AtmImpl();
        System.out.println("init atm balance: " + atm1.getBalance());
        atm1.addBanknotes(Banknote.TEN, 3);
        atm1.addBanknotes(Banknote.TEN, 3);
        Department department1 = new Department();
        department1.addAtm(atm1);
        AtmImpl atm2 = new AtmImpl();
        atm2.addBanknotes(Banknote.TWO_THOUSAND, 4);
        department1.addAtm(atm2);
        System.out.println("department1 before save: " + department1.getBalanceOfAllAtm());
        department1.saveStateOfAllAtm();
        atm1.addBanknotes(Banknote.ONE_HUNDRED, 2);
        atm1.addBanknotes(Banknote.TWO_THOUSAND, 4);
        System.out.println("atm1 before getBanknotes: " + atm1.getBalance());
        System.out.println(atm1.getBanknotes(2100));
        System.out.println("atm1 after getBanknotes: " + atm1.getBalance());
        System.out.println("department1 before reset: " + department1.getBalanceOfAllAtm());
        department1.resetStateOfAllAtm();
        System.out.println("department1 after reset: " + department1.getBalanceOfAllAtm());
    }
}
