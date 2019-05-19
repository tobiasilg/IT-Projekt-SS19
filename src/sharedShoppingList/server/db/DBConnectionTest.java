package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

	public static void main(String[] args) {
		Connection con = DBConnection.connection();
		try {
			System.out.println(con.getMetaData());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
