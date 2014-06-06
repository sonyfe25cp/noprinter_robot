package db;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmailsLoader {
	
	public static void main(String[] args) throws Exception{
		String filePath = "emails.txt";
		File file = new File(filePath);
		FileWriter fw =new FileWriter(file);

		String sql = "select acct_email from pvpgn_BNET;";
		Connection conn =MySqlDataBase.getConnection();
		PreparedStatement psmt = conn.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		List<String> emails = new ArrayList<String>();
		while(rs.next()){
			String email = rs.getString("acct_email");
			if (email!=null && email.length()>7)
				emails.add(email);
		}
		
		for(String email : emails){
			fw.write(email);
			fw.write("\n");
		}
		fw.flush();
		fw.close();
		
	}

}
