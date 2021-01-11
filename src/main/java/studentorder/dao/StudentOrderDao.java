package studentorder.dao;

import studentorder.domain.StudentOrder;
import studentorder.exception.DaoException;

import java.sql.SQLException;

public interface StudentOrderDao
{
    Long saveStudentOrder(StudentOrder studentOrder) throws DaoException, SQLException;

}


