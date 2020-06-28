package ru.otus.java.cachehw;

import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final EventProducer<K, V> producer = new EventProducer<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        producer.event(key, value, "put");
    }

    @Override
    public void remove(K key) {
        producer.event(key, cache.get(key), "remove");
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        producer.event(key, cache.get(key), "get");
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        producer.addListener(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        producer.removeListener(listener);
    }
}
