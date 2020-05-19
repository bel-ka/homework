package ru.otus.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.java.banknotes.Banknotes;
import ru.otus.java.banknotes.OneHundredNominal;
import ru.otus.java.banknotes.TenNominal;
import ru.otus.java.banknotes.TwoThousandNominal;
import ru.otus.java.exception.BanknotesNotFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmTest {
    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new Atm();
    }

    @Test
    void shouldAddBanknotesToAtmCell() {
        atm.addBanknotes(new TwoThousandNominal(), 3);
        assertEquals(6000, atm.getBalance());
    }

    @Test
    void shouldReturnRightBalanceWithoutBanknoteWithdrawal() {
        atm.addBanknotes(new OneHundredNominal(), 3);
        atm.addBanknotes(new TenNominal(), 3);
        atm.getBanknotes(110);
        assertEquals(220, atm.getBalance());
    }

    @Test
    void shouldReturnSumInMinCountOfBanknotes() {
        atm.addBanknotes(new TwoThousandNominal(), 1);
        atm.addBanknotes(new OneHundredNominal(), 200);
        Map<Banknotes, Integer> returnMap = atm.getBanknotes(2000);
        assertEquals(1, returnMap.size());
        assertEquals(TwoThousandNominal.class, returnMap.keySet().stream().findFirst().get().getClass());
        assertEquals(1, returnMap.values().stream().findFirst().get());
    }

    @Test
    void shouldReturnNeedExceptionWhenNoBanknotesInAtm(){
        assertThrows(BanknotesNotFoundException.class,
                () -> atm.getBanknotes(1000));
    }
}