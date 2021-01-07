package studentorder.validator.register;

import studentorder.domain.register.CityRegisterResponse;
import studentorder.domain.Person;
import studentorder.exception.CityRegisterException;
import studentorder.exception.TransportException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException;

}
