package studentorder.dao;

import studentorder.config.Config;
import studentorder.domain.*;
import studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class StudentOrderDaoImpl implements StudentOrderDao {

    public static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    "            student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic," +
                    "    h_date_of_birth, h_passport_serial, h_passport_number, h_passport_date, h_passport_office_id, h_post_index," +
                    "    h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, w_sur_name, w_given_name," +
                    "    w_patronymic, w_date_of_birth, w_passport_serial, w_passport_number, w_passport_date, w_passport_office_id," +
                    "    w_post_index, w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number," +
                    "    certificate_id, register_office_id, marriage_date)" +
                    "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERT_CHILD =
            "INSERT INTO public.jc_student_child(" +
                    " student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth," +
                    " c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code," +
                    " c_building, c_extension, c_apartment)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    private static final String SELECT_ORDERS =
            "SELECT * FROM jc_student_order WHERE student_order_status = 0 ORDER BY student_order_date";


    //TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                //   "jdbc:postgresql://localhost:5432/jc_student", "postgres", "qwe12345");
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DaoException {

        Long result = -1L;
        try (Connection con = getConnection(); // connection надо закрывать, try/catch открывает и закрывает автоматически
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) { //student_order_id получаем id Только что вставленной колонки

            con.setAutoCommit(false); // теперь мы сами управляем транзакциями
            //при true, если ошибка, то все равно часть добавляется

            try {
                // Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                //Husband and Wife
                setParamsForAdult(stmt, 3, studentOrder.getHusband());
                setParamsForAdult(stmt, 18, studentOrder.getWife());


                //Marriage/Wedding
                stmt.setString(33, studentOrder.getMarriageCertificateID());
                stmt.setLong(34, studentOrder.getMarriageOffice().getOfficeId());
                stmt.setDate(35, java.sql.Date.valueOf(studentOrder.getMarriageDate()));

                stmt.executeUpdate(); // возвращает кол-во записей, затронутых изменениями
                ResultSet gkRs = stmt.getGeneratedKeys();
                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                gkRs.close(); // автоматически закрывается, можно не писать

                saveChildren(con, studentOrder, result);
                con.commit(); // соглашаемся с

            } catch (SQLException ex) {
                con.rollback(); // в конце говоритм все коммитим или нет
                throw ex;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection(); // connection надо закрывать, try/catch открывает и закрывает автоматически
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)) {//student_order_id получаем id Только что вставленной колонки
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StudentOrder studentOrder = new StudentOrder();
                fillStudentOrder(rs, studentOrder);
                fillMarriage(rs, studentOrder);

                result.add(studentOrder);
            }

            rs.close();

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return result;
    }


    private void fillStudentOrder(ResultSet rs, StudentOrder studentOrder) throws SQLException {
        studentOrder.setStudentOrderID(rs.getLong("student_order_id"));
        studentOrder.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
    }

    private void fillMarriage(ResultSet rs, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificateID(rs.getString("certificate_id"));
        studentOrder.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        long roID = rs.getLong("register_office_id");
        RegisterOffice ro = new RegisterOffice(roID, "", "");
        studentOrder.setMarriageOffice(ro);
    }


    private void saveChildren(Connection con, StudentOrder studentOrder, Long soId) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
            int counter = 0; // при больших объёмах записей
            for (Child child : studentOrder.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.addBatch(); // добавить в пакет
                counter++;
                if (counter > 10000) { // при больших объёмах записей
                    stmt.executeBatch(); // для каждой команды накапливает изменения и возвращает массив (сбрасывает)
                }
            }
            if (counter > 0) { // при больших объёмах записей
                stmt.executeBatch();
            }

        }
    }

    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start + 4, adult.getPassportSerial());
        stmt.setString(start + 5, adult.getPassportNumber());
        stmt.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start + 13, adult.getUniversity().getUniversityId());
        stmt.setString(start + 14, adult.getStudentID());
    }

    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address address = person.getAddress();
        stmt.setString(start, address.getPostCode());
        stmt.setLong(start + 1, address.getStreet().getStreetCode());
        stmt.setString(start + 2, address.getBuilding());
        stmt.setString(start + 3, address.getExtension());
        stmt.setString(start + 4, address.getApartment());
    }
}