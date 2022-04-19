package cn.wj.lingoes.v2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.wj.lingoes.v2.entity.Dict;
import cn.wj.lingoes.v2.entity.DictItem;

public class SQLiteJDBC {

	public Connection c = null;

	public SQLiteJDBC(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		SQLiteJDBC jdbc = new SQLiteJDBC("/Users/I329722/Desktop/Lingoes.db");
		try {

			jdbc.createTable();
			jdbc.c.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
	}

//	public void delete() throws Exception {
//		c.setAutoCommit(false);
//		System.out.println("Opened database successfully");
//
//		Statement stmt = c.createStatement();
//		String sql = "DELETE from COMPANY where ID=2;";
//		stmt.executeUpdate(sql);
//		c.commit();
//
//		ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
//		while (rs.next()) {
//			int id = rs.getInt("id");
//			String name = rs.getString("name");
//			int age = rs.getInt("age");
//			String address = rs.getString("address");
//			float salary = rs.getFloat("salary");
//			System.out.println("ID = " + id);
//			System.out.println("NAME = " + name);
//			System.out.println("AGE = " + age);
//			System.out.println("ADDRESS = " + address);
//			System.out.println("SALARY = " + salary);
//			System.out.println();
//		}
//		rs.close();
//		stmt.close();
//
//	}

//	public void update() throws Exception {
//		c.setAutoCommit(false);
//		System.out.println("Opened database successfully");
//
//		Statement stmt = c.createStatement();
//		String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
//		stmt.executeUpdate(sql);
//		c.commit();
//
//		ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
//		while (rs.next()) {
//			int id = rs.getInt("id");
//			String name = rs.getString("name");
//			int age = rs.getInt("age");
//			String address = rs.getString("address");
//			float salary = rs.getFloat("salary");
//			System.out.println("ID = " + id);
//			System.out.println("NAME = " + name);
//			System.out.println("AGE = " + age);
//			System.out.println("ADDRESS = " + address);
//			System.out.println("SALARY = " + salary);
//			System.out.println();
//		}
//		rs.close();
//		stmt.close();
//
//	}

	public void selectDictItem(Dict dict,DictItem item) throws Exception {

		c.setAutoCommit(false);

		Statement stmt = c.createStatement();

		
		ResultSet rs = stmt.executeQuery("SELECT * FROM dict WHERE dict_id = " + dict.getId()
				+ " and word = '" + item.getDictWord().replace("'", "''") + "';");
		while (rs.next()) {
			String value = rs.getString("value");
			item.setDictValue(item.getDictValue() + value);
		}
		rs.close();
		stmt.close();
	}

	public void createTable() throws Exception {
		Statement stmt = c.createStatement();

		String sql_dict_header = "CREATE TABLE dict_header( " 
		                                           + "id            INTEGER PRIMARY KEY AUTOINCREMENT, "
				                                   + "name          NCHAR(100) NOT NULL, " 
		                                           + "dict_type     CHAR(4) NOT NULL," 
				                                   + "is_display    CHAR(1), " 
		                                           + "dict_sequence SMALLINT NOT NULL ,"
				                                   + "lang_from     char(2)," 
		                                           + "lang_to       char(2));";

		stmt.executeUpdate(sql_dict_header);
		

		String sql_dict_index = "CREATE TABLE dict_index( " 
				                                   + "id            INTEGER PRIMARY KEY AUTOINCREMENT, "
		                                           + "lang           char(2),"
				                                   + "dict_id        INTEGER NOT NULL, " 
		                                           + "CONSTRAINT fk_id   FOREIGN KEY (dict_id)  REFERENCES dict_header(id));";

		stmt.executeUpdate(sql_dict_index);


		String sql_dict = "CREATE TABLE dict( " 
				                        + "id         INTEGER PRIMARY KEY AUTOINCREMENT, "
		                                + "dict_id    INTEGER NOT NULL, " 
				                        + "word       NCHAR(100) NOT NULL, "
				                        + "value      TEXT," 
				                        + "CONSTRAINT fk_id   FOREIGN KEY (dict_id)  REFERENCES dict_header(id));";
		stmt.executeUpdate(sql_dict);

		stmt.close();
	}

	public void insertDictIteml(String dictName, String word, String value) throws Exception {
		c.setAutoCommit(false);
		Statement stmt = c.createStatement();

		dictName = dictName.replace("'", "''");
		word = word.replace("'", "''");
		value = value.replace("'", "''");

//		String sql = "INSERT INTO dict (NAME,WORD,VALUE) VALUES ('" + dictName + "', '" + word + "', '" + value
//				+ "' );";

		String sql_replace = "REPLACE INTO dict (NAME,WORD,VALUE) VALUES ('" + dictName + "', '" + word + "', '" + value
				+ "' );";

		// System.out.println(sql_replace);
		try {
			stmt.executeUpdate(sql_replace);
		} catch (SQLException e) {
			System.out.println(sql_replace);
			e.printStackTrace();
		}

		stmt.close();
		c.commit();
	}

	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}