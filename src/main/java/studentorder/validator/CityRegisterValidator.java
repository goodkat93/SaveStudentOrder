package studentorder.validator;

import studentorder.domain.Person;
import studentorder.domain.register.AnswerCityRegister;
import studentorder.domain.Child;
import studentorder.domain.register.AnswerCityRegisterItem;
import studentorder.domain.register.CityRegisterResponse;
import studentorder.domain.StudentOrder;
import studentorder.exception.CityRegisterException;
import studentorder.exception.TransportException;
import studentorder.validator.register.CityRegisterChecker;
import studentorder.validator.register.FakeCityRegisterChecker;

import java.util.List;

public class CityRegisterValidator {
    public static final String IN_CODE = "NO_GRN";

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        AnswerCityRegister ans = new AnswerCityRegister();
        ans.addItem(checkPerson(studentOrder.getHusband()));
        ans.addItem(checkPerson(studentOrder.getWife()));
//            for (int i = 0; i < studentOrder.getChildren().size(); i++) {
//                CityRegisterCheckerResponse cans = personChecker.checkPerson(children.get(i));
//            }
//
//            for(Iterator<Child> it = children.iterator(); it.hasNext(); ){ // итератор указатель = устанавливается на начало списка(бегает по детям) ; имеет ли итератор этот элемент
//                Child child = it.next(); // получаем элемент, который идёт следующий за итератором
//                CityRegisterCheckerResponse cans = personChecker.checkPerson(child); // проверяем
//            }
        for (Child child : studentOrder.getChildren()) { // объявляю переменную : которая перебегает с одного элемента на другой
            ans.addItem(checkPerson(child));
        }

        return ans;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem.CityStatus status = null; //null убрали, потому что status в любом случае будет определен
        AnswerCityRegisterItem.CityError error = null; // тут мы определяем только в двух вариантах, поэтому есть шанс null
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting() ?
                    AnswerCityRegisterItem.CityStatus.YES :
                    AnswerCityRegisterItem.CityStatus.NO;
            /*         } catch (Exception ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            if(ex instanceof CityRegisterException) {
                CityRegisterException e = (CityRegisterException) ex;
                error = new AnswerCityRegisterItem.CityError(e.getCode(), ex.getMessage());
            }
            if(ex instanceof TransportException) {
                TransportException e = (TransportException) ex;
                error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
            }
*/
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        } catch (TransportException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }
          catch (Exception ex){
              ex.printStackTrace(System.out);
              status = AnswerCityRegisterItem.CityStatus.ERROR;
              error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
          }
        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);

        return ans;
    }
}
