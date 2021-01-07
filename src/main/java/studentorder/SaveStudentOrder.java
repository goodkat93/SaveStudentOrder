package studentorder;

import studentorder.dao.DictionaryDaoImpl;
import studentorder.domain.*;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder
{
    public static void main(String[] args) throws Exception { // поменять если что (было DaoException)
        List<Street> str = new DictionaryDaoImpl().findStreets("про");
        for (Street street : str){
            System.out.println(street.getStreetName());
        }
        List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
        for(PassportOffice p : po) {
            System.out.println(p.getOfficeName());
        }
            List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
        for(RegisterOffice r : ro){
            System.out.println(r.getOfficeName());
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
        RegisterOffice ro1 = new RegisterOffice(1L, "", "");
        studentOrder.setMarriageOffice(ro1);

        Street street = new Street(1L, "First Street");

        Address address = new Address("195000", street, "12", "","142");

        //муж
        Adult husband = new Adult("Архипов", "Андрей", "Петрович", LocalDate.of(1993, 8, 24));
        husband.setPassportSerial("" + (1000+ id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2017, 8, 16));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentID(""+(100000 + id));
        husband.setAddress(address);
        //жена
        Adult wife = new Adult("Архипова", "Екатерина", "Романовна", LocalDate.of(1991, 3, 12));
        wife.setPassportSerial("" + (2000+ id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2015, 3, 15));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        husband.setIssueDepartment(po2);
        wife.setStudentID(""+(200000 + id));
        wife.setAddress(address);
        //ребенок 1
        Child child1 = new Child("Архипова", "Мирослава", "Олеговна", LocalDate.of(2018,7,9));
        child1.setCertificateNumber("" + (30000 + id));
        child1.setIssueDate(LocalDate.of(2018,8,28));
        RegisterOffice ro2 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        //ребенок 2
        Child child2 = new Child("Архипов", "Ярослав", "Олегович", LocalDate.of(2018,7,9));
        child2.setCertificateNumber("" + (30000 + id));
        child2.setIssueDate(LocalDate.of(2018,8,28));
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
