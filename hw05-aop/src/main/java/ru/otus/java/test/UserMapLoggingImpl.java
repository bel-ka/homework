package ru.otus.java.test;

import ru.otus.java.annotations.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserMapLoggingImpl implements UserMapLoggingInterface {
    private Map<String, String> userMap = new HashMap<>();

    @Override
    public void addUserToMap(String userName) {
        userMap.put(UUID.randomUUID().toString(), userName);
        System.out.println("User " + userName + " add to map");
    }

    @Log
    @Override
    public void findUserIdInMap(String userName) {
        Optional<Map.Entry<String, String>> userEntrySet =
                userMap.entrySet().stream()
                        .filter(v -> userName.equals(v.getValue()))
                        .findFirst();

        String uid = (userEntrySet.isPresent()) ? userEntrySet.get().getKey() : "not found";

        System.out.println(userName + " ID: " + uid);
    }
}
