package _27102022.stackAndQueue;

import java.util.ArrayList;

public interface Stack2<T> {
    void push(T item);//add
    T pop();//get
    boolean isEmpty();
}

class StackImpl<T> implements Stack2<T> {
    private final ArrayList<T> list = new ArrayList<>();

    @Override
    public void push(T item) {
        list.add(item);
    }

    @Override
    public T pop() {
        return list.remove(list.size()-1);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}