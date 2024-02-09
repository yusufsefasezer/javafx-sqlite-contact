package com.yusufsezer.contract;

import java.util.List;

public interface IRepository<T, I> {

    T get(I index);

    List<T> getAll();

    boolean add(T obj);

    T update(I index, T obj);

    T remove(I index);

}
