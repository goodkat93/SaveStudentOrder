package studentorder.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name){
        if(properties.isEmpty()) {
            //try(FileInputStream is = new FileInputStream("src/dao.properties")) { // открываем файл как поток байтов try/catch открывает и закрывает автоматически
            try(InputStream is = Config.class.getClassLoader()
                    .getResourceAsStream("src/dao.properties")){
                properties.load(is);

            } catch (IOException ex){
                ex.printStackTrace();
                throw new RuntimeException(ex); // при неудачной загрузке файла - всё упадет(приложение не запустится, потому что это бессмысленно)
            }
        }
        return properties.getProperty(name);
    }
}
