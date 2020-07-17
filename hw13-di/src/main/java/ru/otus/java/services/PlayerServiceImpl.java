package ru.otus.java.services;

import lombok.AllArgsConstructor;
import ru.otus.java.model.Player;

@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final IOService ioService;

    @Override
    public Player getPlayer() {
        ioService.out("Представьтесь пожалуйста");
        String playerName = ioService.readLn("Введите имя: ");
        return new Player(playerName);
    }
}
