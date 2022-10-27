package _27102022.stackAndQueue;

import java.util.ArrayList;

public interface Queue1<T> {
    void add(T item);
    T remove();
    boolean isEmpty();
}

class QueueImpl<T> implements Queue1<T> {
    private final ArrayList<T> list = new ArrayList<>();

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public T remove() {
        //todo сначала провести проверку потом удалять
        return list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}