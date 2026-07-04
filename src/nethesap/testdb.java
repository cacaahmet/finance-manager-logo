package nethesap;

import java.sql.Connection;

public class testdb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Program başladı");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Veritabanı bağlantısı başarılı! 🎉");
        } else {
            System.out.println("Bağlantı kurulamadı! ❌");
        }
	}

}
