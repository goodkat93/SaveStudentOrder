package studentorder;

import studentorder.dao.StudentOrderDaoImpl;
import studentorder.dao.StudentOrderDao;
import studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception { // поменять если что (было DaoException)
//        List<Street> str = new DictionaryDaoImpl().findStreets("про");
//        for (Street street : str){
//            System.out.println(street.getStreetName());
//        }
//        List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
//        for(PassportOffice p : po) {
//            System.out.println(p.getOfficeName());
//        }
//            List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
//        for(RegisterOffice r : ro){
//            System.out.println(r.getOfficeName());
//        }
//        List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
//        for (CountryArea c : ca1) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
//        System.out.println("-------------------->>");
//        List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
//        for (CountryArea c : ca2) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
//        System.out.println("-------------------->>");
//        List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
//        for (CountryArea c : ca3) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
//        System.out.println("-------------------->>");
//        List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
//        for (CountryArea c : ca4) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }


        StudentOrder s = buildStudentOrder(10);
        StudentOrderDao dao = new StudentOrderDaoImpl();
        Long id = dao.saveStudentOrder(s);
//        System.out.println(id);

        List<StudentOrder> soList = dao.getStudentOrders();


        for (StudentOrder studentOrder :
                soList) {
            System.out.println(studentOrder.getStudentOrderID());
        }

    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("sSO:");
        return answer;

    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderID(id);
        studentOrder.setMarriageCertificateID("" + (123412515 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016, 7, 4));
        RegisterOffice ro1 = new RegisterOffice(1L, "", "");
        studentOrder.setMarriageOffice(ro1);

        Street street = new Street(1L, "First Street");

        Address address = new Address("195000", street, "12", "", "142");

        //муж
        Adult husband = new Adult("Архипов", "Андрей", "Петрович", LocalDate.of(1993, 8, 24));
        husband.setPassportSerial("" + (1000 + id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2017, 8, 16));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentID("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, " "));
        husband.setStudentID("WW12345");
        //жена
        Adult wife = new Adult("Архипова", "Екатерина", "Романовна", LocalDate.of(1991, 3, 12));
        wife.setPassportSerial("" + (2000 + id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2015, 3, 15));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentID("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, " "));
        wife.setStudentID("WW12345");
        //ребенок 1
        Child child1 = new Child("Архипова", "Мирослава", "Олеговна", LocalDate.of(2018, 7, 9));
        child1.setCertificateNumber("" + (30000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11));
        RegisterOffice ro2 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        //ребенок 2
        Child child2 = new Child("Архипов", "Ярослав", "Олегович", LocalDate.of(2018, 7, 9));
        child2.setCertificateNumber("" + (30000 + id));
        child2.setIssueDate(LocalDate.of(2018, 8, 28));
        RegisterOffice ro3 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.addChild(child1);
        studentOrder.addChild(child2);

        return studentOrder;
    }
}
