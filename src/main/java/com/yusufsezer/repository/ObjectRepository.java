package com.yusufsezer.repository;

import com.yusufsezer.contract.IRepository;
import java.util.List;

public class ObjectRepository<T, I extends Number> implements IRepository<T, I> {

    private final List<T> items;

    public ObjectRepository(List<T> items) {
        this.items = items;
    }

    @Override
    public T get(I index) {
        return this.items.get(index.intValue());
    }

    @Override
    public List<T> getAll() {
        return this.items;
    }

    @Override
    public boolean add(T obj) {
        return this.items.add(obj);
    }

    @Override
    public T update(I index, T obj) {
        return this.items.set(index.intValue(), obj);
    }

    @Override
    public T remove(I index) {
        return this.items.remove(index.intValue());
    }

}
