package ru.otus.java.atm;

import ru.otus.java.banknotes.Banknote;
import ru.otus.java.exception.BanknotesNotFoundException;
import ru.otus.java.memento.Originator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AtmImpl implements Atm {
    private final Map<Banknote, Integer> banknotesCell = new TreeMap<>(Collections.reverseOrder());
    private Originator originator = new Originator();

    public AtmImpl(Atm atm) {
        banknotesCell.putAll(atm.getBanknotesCell());
    }

    public AtmImpl() {
    }

    @Override
    public Map<Banknote, Integer> getBanknotesCell() {
        return banknotesCell;
    }

    @Override
    public void addBanknotes(Banknote banknote, Integer count) {
        banknotesCell.compute(banknote, (key, oldValue) -> (oldValue == null) ? count : oldValue + count);
    }

    @Override
    public Map<Banknote, Integer> getBanknotes(int requiredAmount) {
        if (getBalance() < requiredAmount) {
            throwAtmException();
        }

        Map<Banknote, Integer> returnMapOfBanknotes = new HashMap<>();
        for (Map.Entry<Banknote, Integer> cell:banknotesCell.entrySet()) {
            var amountOfResultMap = returnMapOfBanknotes.entrySet().stream()
                    .mapToInt((k) -> k.getKey().getNominal() * k.getValue()).sum();
            var needAmount = requiredAmount - amountOfResultMap;
            int returnCount = needAmount / cell.getKey().getNominal();
            if (returnCount > cell.getValue()) {
                throwAtmException();
            }
            if (needAmount != 0 && returnCount > 0) {
                returnMapOfBanknotes.put(cell.getKey(), returnCount);
            }
        }

        reduceBanknotesInCell(returnMapOfBanknotes);

        return returnMapOfBanknotes;
    }

    private void reduceBanknotesInCell(Map<Banknote, Integer> mapOfBanknotes) {
        mapOfBanknotes.forEach((banknotes, count) ->
                banknotesCell.keySet().forEach((banknotesInCell) -> {
                    if (banknotes.getNominal() == banknotesInCell.getNominal()) {
                        banknotesCell.replace(banknotesInCell, (banknotesCell.get(banknotesInCell) - count));
                    }
                })
        );
    }

    @Override
    public int getBalance() {
        return banknotesCell.entrySet().stream().mapToInt((k) -> k.getKey().getNominal() * k.getValue()).sum();
    }

    private void throwAtmException() {
        throw new BanknotesNotFoundException(
                String.format("Запрошенная сумма не найдена. В АТМ есть банкноты следующего номинала: %s",
                        banknotesCell.keySet().stream()
                                .map(key -> key.getNominal() + "=" + banknotesCell.get(key))
                                .sorted()
                                .collect(Collectors.joining(", "))
                )
        );
    }

    @Override
    public String toString() {
        return "AtmImpl{" +
                "banknotesCell=" + banknotesCell +
                '}';
    }

    public void saveState() {
        originator.saveState(this);
    }

    public void resetToSavedState() {
        originator.resetToSavedState(this);
    }
}