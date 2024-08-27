package rs.ac.bg.fon.employee.service;

import rs.ac.bg.fon.employee.exception.type.NotFoundException;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> findAll();
    T findById(Object id) throws NotFoundException;
    T save(T t) throws Exception;
    T update(T t) throws Exception;
    void deleteById(Object id) throws Exception;
}
