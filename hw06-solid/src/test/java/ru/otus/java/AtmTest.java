package ru.otus.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.java.banknotes.Banknote;
import ru.otus.java.exception.BanknotesNotFoundException;

import java.util.HashMap;
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
        atm.addBanknotes(Banknote.TWO_THOUSAND, 3);
        assertEquals(6000, atm.getBalance());
    }

    @Test
    void shouldReturnRightBalanceWithoutBanknoteWithdrawal() {
        Map<Integer, Map.Entry<Banknote, Integer>> banknotesToAtm = new HashMap<>();
        banknotesToAtm.put(1, Map.entry(Banknote.ONE_HUNDRED, 3));
        banknotesToAtm.put(2, Map.entry(Banknote.TEN, 3));
        atm.addBanknotes(banknotesToAtm.get(1).getKey(), banknotesToAtm.get(1).getValue());
        atm.addBanknotes(banknotesToAtm.get(2).getKey(), banknotesToAtm.get(2).getValue());
        int wouldGetAmount = 110;
        atm.getBanknotes(wouldGetAmount);
        int shouldBeBalance = banknotesToAtm.get(1).getKey().getNominal() * banknotesToAtm.get(1).getValue()
                + banknotesToAtm.get(2).getKey().getNominal() * banknotesToAtm.get(2).getValue()
                - wouldGetAmount;
        assertEquals(shouldBeBalance, atm.getBalance());
    }

    @Test
    void shouldReturnSumInMinCountOfBanknotes() {
        atm.addBanknotes(Banknote.TWO_THOUSAND, 1);
        atm.addBanknotes(Banknote.ONE_HUNDRED, 200);
        Map<Banknote, Integer> returnMap = atm.getBanknotes(2000);
        assertEquals(1, returnMap.size());
        assertEquals(Banknote.TWO_THOUSAND, returnMap.keySet().stream().findFirst().get());
        assertEquals(1, returnMap.values().stream().findFirst().get());
    }

    @Test
    void shouldReturnNeedExceptionWhenNoBanknotesInAtm(){
        assertThrows(BanknotesNotFoundException.class,
                () -> atm.getBanknotes(1000));
    }
}