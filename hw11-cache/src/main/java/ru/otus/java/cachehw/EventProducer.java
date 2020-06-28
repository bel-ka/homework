package ru.otus.java.cachehw;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventProducer<K, V> {

    private final List<SoftReference<HwListener<K, V>>> listeners = new ArrayList<>();

    void addListener(HwListener<K, V> listener) {
        listeners.add(new SoftReference<>(listener));
    }

    void removeListener(HwListener<K, V> listener) {
        listeners.removeIf(x -> x.get() != null && Objects.requireNonNull(x.get()).equals(listener));
    }

    void event(K key, V value, String action) {
        listeners.stream().map(SoftReference::get).filter(Objects::nonNull).forEach(x -> x.notify(key, value, action));
    }
}
