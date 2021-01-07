package studentorder;

import studentorder.dao.DictionaryDaoImpl;
import studentorder.domain.*;
import studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder
{
    public static void main(String[] args) throws Exception { // поменять если что (было DaoException)
        List<Street> d = new DictionaryDaoImpl().findStreets("str");
        for (Street street : d){
            System.out.println(street.getStreetName());
        }





//        StudentOrder studentOrder = new StudentOrder();
//        long ans = saveStudentOrder(studentOrder);
//        System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("sSO:");
        return answer;

    }

    public static StudentOrder buildStudentOrder(long id){
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderID(id);
        studentOrder.setMarriageCertificateID("" + (123412515 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016,7,4));
        studentOrder.setMarriageOffice("ОТДЕЛ ЗАГС");

        Street street = new Street(1L, "First Street");

        Address address = new Address("195000", street, "12", "","142");

        //муж
        Adult husband = new Adult("Архипов", "Андрей", "Петрович", LocalDate.of(1993, 8, 24));
        husband.setPassportSerial("" + (1000+ id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2017, 8, 16));
        husband.setIssueDepartment("ОТДЕЛ РОВД ПО ЧЕЛЯБИНСКОЙ ОБЛАСТИ №" + id);
        husband.setStudentID(""+(100000 + id));
        husband.setAddress(address);
        //жена
        Adult wife = new Adult("Архипова", "Екатерина", "Романовна", LocalDate.of(1991, 3, 12));
        wife.setPassportSerial("" + (2000+ id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2015, 3, 15));
        wife.setIssueDepartment("ОТДЕЛ РОВД ПО ЧЕЛЯБИНСКОЙ ОБЛАСТИ №" + id);
        wife.setStudentID(""+(200000 + id));
        wife.setAddress(address);
        //ребенок 1
        Child child1 = new Child("Архипова", "Мирослава", "Олеговна", LocalDate.of(2018,7,9));
        child1.setCertificateNumber("" + (30000 + id));
        child1.setIssueDate(LocalDate.of(2018,8,28));
        child1.setIssueDepartment("Отдел ЗАГС");
        child1.setAddress(address);
        //ребенок 2
        Child child2 = new Child("Архипов", "Ярослав", "Олегович", LocalDate.of(2018,7,9));
        child2.setCertificateNumber("" + (30000 + id));
        child2.setIssueDate(LocalDate.of(2018,8,28));
        child2.setIssueDepartment("Отдел ЗАГС");
        child2.setAddress(address);

        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.addChild(child1);
        studentOrder.addChild(child2);

        return studentOrder;
    }
}
