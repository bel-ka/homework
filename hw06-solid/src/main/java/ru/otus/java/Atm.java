package ru.otus.java;

import ru.otus.java.banknotes.Banknotes;
import ru.otus.java.exception.BanknotesNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Atm {
    private final Map<Banknotes, Integer> banknotesCell = new HashMap<>();

    public void addBanknotes(Banknotes banknotes, Integer count) {
        Optional<Banknotes> banknotesInCell = banknotesCell.keySet().stream()
                .filter(k -> k.getNominal() == banknotes.getNominal())
                .findAny();
        if (banknotesInCell.isPresent()) {
            banknotesCell.replace(banknotesInCell.get(),
                    banknotesCell.get(banknotesInCell.get()) + count);
        } else {
            banknotesCell.put(banknotes, count);
        }
    }

    public Map<Banknotes, Integer> getBanknotes(int requiredAmount) {
        if (getBalance() < requiredAmount) {
            throwAtmException();
        }

        Map<Banknotes, Integer> returnMapOfBanknotes = new HashMap<>();
        banknotesCell.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<Banknotes, Integer> e) -> e.getKey().getNominal()).reversed())
                .forEach((cell) -> {
                    var amountOfResultMap = returnMapOfBanknotes.entrySet().stream()
                            .mapToInt((k) -> k.getKey().getNominal() * k.getValue()).sum();
                    var needAmount = requiredAmount - amountOfResultMap;
                    int returnCount = needAmount / cell.getKey().getNominal();
                    if (returnCount > cell.getValue()) {
                        throwAtmException();
                    }
                    if (needAmount != 0 && returnCount > 0) {
                        try {
                            returnMapOfBanknotes.put(initClassBanknotes(cell.getKey().getClass().getName()), returnCount);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

        reduceBanknotesInCell(returnMapOfBanknotes);

        return returnMapOfBanknotes;
    }

    private void reduceBanknotesInCell(Map<Banknotes, Integer> mapOfBanknotes) {
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

    private Banknotes initClassBanknotes(String className) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return (Banknotes) clazz.getDeclaredConstructor().newInstance();
    }
}