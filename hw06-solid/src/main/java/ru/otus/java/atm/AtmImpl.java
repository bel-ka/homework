package ru.otus.java.atm;

import ru.otus.java.banknotes.Banknote;
import ru.otus.java.exception.AtmException;
import ru.otus.java.exception.BanknotesNotFoundException;
import ru.otus.java.memento.Memento;
import ru.otus.java.memento.Originator;

import java.util.*;
import java.util.stream.Collectors;

public class AtmImpl implements Atm, Originator {
    private final Map<Banknote, Integer> banknotesCell = new TreeMap<>(Collections.reverseOrder());

    private Memento memento;

    public AtmImpl(AtmImpl atm) {
        banknotesCell.putAll(atm.getBanknotesCell());
    }

    public AtmImpl() {
    }

    public Memento getMemento() {
        return memento;
    }

    private Map<Banknote, Integer> getBanknotesCell() {
        return banknotesCell;
    }

    public void addBanknotes(Banknote banknote, Integer count) {
        banknotesCell.compute(banknote, (key, oldValue) -> (oldValue == null) ? count : oldValue + count);
    }

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

    @Override
    public void saveState() {
        memento = new Memento(this);
    }

    @Override
    public void resetToSavedState() {
        if (memento == null) {
            throw new AtmException("Отсутствует ранее сохраненное состояние банкомата.");
        } else {
            banknotesCell.clear();
            banknotesCell.putAll(memento.getState().getBanknotesCell());
        }
    }
}