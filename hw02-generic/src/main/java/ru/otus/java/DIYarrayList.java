package ru.otus.java;

import java.util.*;
import java.util.function.Consumer;

public class DIYarrayList<T> implements List<T> {
    private static final Object[] DEFAULT_CAPACITY_EMPTY_DATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private Object[] data;
    private int size;
    protected int modCount = 0;

    public DIYarrayList() {
        this.data = DEFAULT_CAPACITY_EMPTY_DATA;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<T> iterator() {
        return new DIYIterator();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        modCount++;
        add(size, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index == data.length) {
            data = Arrays.copyOf(data,
                    setNewCapacity(++size));
        }
        data[index] = element;
        size = ++index;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    private int setNewCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        int oldCapacity = data.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (data == DEFAULT_CAPACITY_EMPTY_DATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : getMaxCapacity(minCapacity);
    }

    private static int getMaxCapacity(int minCapacity) {
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T value = elementData(index);
        data[index] = element;
        return value;
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ListIterator<T> listIterator() {
        return new DIYListIterator(0);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ListIterator<T> listIterator(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        return new DIYListIterator(index);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    T elementData(int index) {
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    static <T> T elementAt(Object[] es, int index) {
        return (T) es[index];
    }

    private class DIYListIterator implements ListIterator<T> {
        int cursor;
        int lastIndex = -1;
        int expectedModCount = modCount;

        DIYListIterator(int index) {
            super();
            cursor = index;
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkConcurrentMod();
            int index = cursor;
            if (index >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.data;
            if (index >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = index + 1;
            return (T) elementData[lastIndex = index];
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            if (lastIndex < 0)
                throw new IllegalStateException();
            checkConcurrentMod();

            DIYarrayList.this.set(lastIndex, t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            final int size = DIYarrayList.this.size;
            int index = cursor;
            if (index < size) {
                final Object[] es = data;
                if (index >= es.length)
                    throw new ConcurrentModificationException();
                for (; index < size && modCount == expectedModCount; index++)
                    action.accept(elementAt(es, index));
                cursor = index;
                lastIndex = index - 1;
                checkConcurrentMod();
            }
        }

        final void checkConcurrentMod() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class DIYIterator implements Iterator<T> {
        int cursor;
        int lastIndex = -1;
        int expectedModCount = modCount;

        DIYIterator() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (cursor >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.data;
            if (cursor >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = cursor + 1;
            return (T) elementData[lastIndex = cursor - 1];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            throw new UnsupportedOperationException();
        }
    }
}
