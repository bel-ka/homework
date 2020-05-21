package ru.otus.java;

import ru.otus.java.banknotes.Banknote;
import ru.otus.java.exception.BanknotesNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class Atm {
    private final Map<Banknote, Integer> banknotesCell = new TreeMap<>(Collections.reverseOrder());

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
}