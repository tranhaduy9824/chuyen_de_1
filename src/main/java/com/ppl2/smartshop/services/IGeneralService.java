package com.ppl2.smartshop.services;

import java.util.List;


public interface IGeneralService<T,U> {
    List<T> findAll();

    T findById(U id);

    T save(T t);

    void remove(U id);
}
