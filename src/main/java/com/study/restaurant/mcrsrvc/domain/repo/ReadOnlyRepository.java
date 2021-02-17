package com.study.restaurant.mcrsrvc.domain.repo;

import com.study.restaurant.mcrsrvc.domain.model.Entity;

import java.util.Collection;

/**
 * 存储库的只读接口
 *
 * @author Darcy
 *         Created by Darcy on 2017/7/17.
 */
public interface ReadOnlyRepository<TE, T> {
    boolean contains(T id);

    Entity get(T id);

    Collection<TE> getAll();
}
