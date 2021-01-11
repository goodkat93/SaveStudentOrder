package studentorder;

import studentorder.domain.*;
import studentorder.domain.register.AnswerCityRegister;
import studentorder.mail.MailSender;
import studentorder.validator.ChildrenValidator;
import studentorder.validator.CityRegisterValidator;
import studentorder.validator.MarriageValidator;
import studentorder.validator.StudentValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {

    private CityRegisterValidator cityRegisterVal;
    private MarriageValidator marriageVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator() {
        cityRegisterVal = new CityRegisterValidator();
        marriageVal = new MarriageValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }


    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll() {
        List<StudentOrder> studentOrderList = readStudentOrders();

        for (StudentOrder studentOrder : studentOrderList) {
            checkOneOrder(studentOrder);
        }
    }

    public List<StudentOrder> readStudentOrders() {
        List<StudentOrder> studentOrderList = new LinkedList<>();

        for (int c = 0; c < 5; c++) {
            StudentOrder studentOrder = SaveStudentOrder.buildStudentOrder(c);
            studentOrderList.add(studentOrder);
        }

        return studentOrderList;
    }

    public void checkOneOrder(StudentOrder studentOrder) {
        AnswerCityRegister cityAnswer = checkCityRegister(studentOrder);
//        AnswerMarriage marAnswer = checkMarriage(studentOrder);
//        AnswerChildren childAnswer = checkChildren(studentOrder);
//        AnswerStudent studentAnswer = checkStudent(studentOrder);
//
//        sendMail(studentOrder);
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterVal.checkCityRegister(studentOrder);
    }

    public AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        return marriageVal.checkMarriage(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenVal.checkChildren(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        return studentVal.checkStudent(studentOrder);
    }

    public void sendMail(StudentOrder studentOrder) {
        mailSender.sendMail(studentOrder);
    }

}
