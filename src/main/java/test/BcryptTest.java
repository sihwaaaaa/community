package test;

import org.mindrot.bcrypt.BCrypt;

public class BcryptTest {
	public static void main(String[] args) {
		String pw = "0000";
		String result = BCrypt.hashpw(pw, BCrypt.gensalt(8));
		String result2 = BCrypt.hashpw(pw, BCrypt.gensalt(8));
		String result3 = BCrypt.hashpw(pw, BCrypt.gensalt(8));
		
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
		
		System.out.println(BCrypt.checkpw("1111", result));
		System.out.println(BCrypt.checkpw("0000", result2));
		System.out.println(BCrypt.checkpw("0000", result3));
		
		String resultCopy = "$2a$08$ZKmiQ1yNqAWCeKFwp6XCpOgyEhclGHfqPz6epEVlBf9BXkBeK09lS";
		System.out.println(BCrypt.checkpw("0000", resultCopy));
		
	}
}
