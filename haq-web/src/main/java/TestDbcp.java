import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @作者：刘新运
 * @日期：2017/6/25 18:42
 * @描述：类
 */

public class TestDbcp {

    private static DataSource dataSource=null;

    static{
        try {
            Properties prop=new Properties();
            prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            prop.setProperty("url", "jdbc:mysql://172.16.1.13:3306/haq");
            prop.setProperty("username", "root");
            prop.setProperty("password", "haq666team");
            prop.setProperty("initialSize", "100");
            prop.setProperty("maxActive", "500");
            prop.setProperty("minIdle", "100");
            prop.setProperty("maxWait", "60000");
            prop.setProperty("connectionProperties", "useUnicode=true;characterEncoding=utf8");
            prop.setProperty("defaultAutoCommit", "true");
            prop.setProperty("defaultTransactionIsolation", "READ_COMMITTED");
            System.out.println(prop.toString());
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection(){
        try {
            Connection conn = dataSource.getConnection();
            while(conn==null){
                conn = dataSource.getConnection();
            }
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void releaseConn(Connection connection, PreparedStatement statement,
                                   ResultSet resultSet) {
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet=null;
        }

        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement=null;
        }

        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection=null;
        }
    }

    public static void closeConn(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection=null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from t_class");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
