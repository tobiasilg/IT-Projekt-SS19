package sharedShoppingList.server.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import sharedShoppingList.shared.bo.User;

/**
* ACHTUNG!!!
* Dieser Mapper ist nicht für den Produktivbetrieb gedacht!
* Vor der Integration des Google-Logins soll damit aber bereits 
* ein Testen des Users und der weiteren BOs möglich sein.
* Dazu werden die Attribute hardgecoded.
* @author Tobias Ilg
*/


public class TestUserMapper {

	public static void main(String[] args) {

		final UserMapper userMapper = UserMapper.userMapper();
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		if (userMapper.findById(9999) == null) {

		User testuser = new User();

		//Hardcoding der Attribute:
		
		testuser.setId(9999);

		testuser.setGroupid(9999);
		
		testuser.setName("TESTNAME");

		testuser.setUsername("TESTUSERNAME");

		testuser.setGmail("TEST@USER");
		
		testuser.setLoggedIn(true);
		
		testuser.setCreateDate(ts);
		
		testuser.setModDate(ts);
		
		//Transfer des Objekts in die DB
		
		userMapper.insert(testuser);
		
		}
		
	}

}
