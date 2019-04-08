package ru.tolymhlv.testrest.converters;

public interface Converter<M, V> {
    V getView(M model);

    M getModel(V view);
}
