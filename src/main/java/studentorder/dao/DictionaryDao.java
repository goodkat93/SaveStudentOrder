package studentorder.dao;

import studentorder.domain.Street;
import studentorder.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreets(String pattern) throws DaoException;
}
