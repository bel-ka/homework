package ru.otus.java.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventProducer<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    void removeListener(HwListener<K, V> listener) {
        listeners.removeIf(x -> x.equals(listener));
    }

    void event(K key, V value, String action) {
        listeners.stream().filter(Objects::nonNull).forEach(x -> {
            try {
                x.notify(key, value, action);
            } catch (Exception e) {
                logger.debug(e.getLocalizedMessage());
            }
        });
    }
}
