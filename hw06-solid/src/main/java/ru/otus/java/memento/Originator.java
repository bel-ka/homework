package ru.otus.java.memento;

public interface Originator {
    void saveState();
    void resetToSavedState();
}
