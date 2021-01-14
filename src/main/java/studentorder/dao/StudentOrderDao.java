package studentorder.dao;

import studentorder.domain.StudentOrder;
import studentorder.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface StudentOrderDao
{
    Long saveStudentOrder(StudentOrder studentOrder) throws DaoException;
    List<StudentOrder> getStudentOrders() throws DaoException;

}


