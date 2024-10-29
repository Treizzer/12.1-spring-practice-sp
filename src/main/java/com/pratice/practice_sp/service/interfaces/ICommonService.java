package com.pratice.practice_sp.service.interfaces;

import java.util.List;

public interface ICommonService<T, TI, TU> {

    List<T> findAll();

    T findById(Long id);

    T add(TI insertDto);

    T update(TU updateDto, Long id);

    T deleteById(Long id);

}
