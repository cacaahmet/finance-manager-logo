package nethesap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    //  SQL Server bağlantı 
    private static final String URL = "jdbc:sqlserver://localhost:1433;"
    		+ "databaseName=NETHESAP;encrypt=false;";
    private static final String kullanici="sa";
    private static final String sifre="root";
    
    // Bağlantıyı döndüren metot
    public static Connection getConnection() {
        System.out.println("getConnection() metodu çalıştı");
        try {
            Connection conn = DriverManager.getConnection(URL, kullanici, sifre);
            System.out.println("✅ Veritabanına bağlantı başarılı!");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Bağlantı hatası: " + e.getMessage());
            return null;
        }
    }

}