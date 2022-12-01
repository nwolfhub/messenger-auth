package org.nwolfhub.shared.database.model;

import java.util.List;

public interface Dao {
    Object get(Integer id);
    void save(Object obj);
    void update(Object obj);
    void delete(Object obj);
    List getAll();
}
