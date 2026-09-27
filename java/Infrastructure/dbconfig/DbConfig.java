package Infrastructure.dbconfig;

import java.io.IOException;
import java.util.Properties;

public final class DbConfig
{
    private  static  final Properties props = new Properties();

    static {
        try (var in = DbConfig.class.getResourceAsStream("/db.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String url()      { return props.getProperty("db.url"); }
    public static String user()     { return props.getProperty("db.user"); }
    public static String password() { return props.getProperty("db.password"); }


}
