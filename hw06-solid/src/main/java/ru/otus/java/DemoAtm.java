package ru.otus.java;

import ru.otus.java.banknotes.OneHundredNominal;
import ru.otus.java.banknotes.TenNominal;
import ru.otus.java.banknotes.TwoThousandNominal;

public class DemoAtm {
    public static void main(String[] args) {
        Atm atm = new Atm();
        System.out.println(atm.getBalance());
        atm.addBanknotes(new TenNominal(), 3);
        atm.addBanknotes(new OneHundredNominal(), 2);
        atm.addBanknotes(new TwoThousandNominal(), 4);
        System.out.println(atm.getBalance());
        atm.getBanknotes(2100);
        System.out.println(atm.getBalance());
    }
}
