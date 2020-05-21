package ru.otus.java;

import ru.otus.java.banknotes.Banknote;

public class DemoAtm {
    public static void main(String[] args) {
        Atm atm = new Atm();
        System.out.println(atm.getBalance());
        atm.addBanknotes(Banknote.TEN, 3);
        atm.addBanknotes(Banknote.TEN, 3);
        atm.addBanknotes(Banknote.ONE_HUNDRED, 2);
        atm.addBanknotes(Banknote.TWO_THOUSAND, 4);
        System.out.println(atm.getBalance());
        System.out.println(atm.getBanknotes(2100));
        System.out.println(atm.getBalance());
    }
}
