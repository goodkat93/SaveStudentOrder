package studentorder.dao;

import studentorder.config.Config;
import studentorder.domain.Street;
import studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao
{
        private static final String GET_STREET = "SELECT street_code, street_name " +
        "FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";

        private Connection getConnection() throws SQLException {
            Connection con = DriverManager.getConnection(
                 //   "jdbc:postgresql://localhost:5432/jc_student", "postgres", "qwe12345");
                    Config.getProperty(Config.DB_URL),
                    Config.getProperty(Config.DB_LOGIN),
                    Config.getProperty(Config.DB_PASSWORD));
            return con;
        }

    public List<Street> findStreets(String pattern) throws DaoException {
            List<Street> result = new LinkedList<>();

            try(Connection con = getConnection(); // connection надо закрывать, try/catch открывает и закрывает автоматически
                PreparedStatement stmt = con.prepareStatement(GET_STREET)) {

                stmt.setString(1, "%" + pattern + "%");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
                    result.add(str);
                }
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        return result;
    }
}