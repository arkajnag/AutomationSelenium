package com.Automation.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import com.Automation.baseClass.BaseClass;
import com.mysql.cj.jdbc.Driver;

public interface DataBaseConnectUtils {
	
	
	@SuppressWarnings("static-access")
	public static Function<String,Object> getDatabaseConnection=sqlQuery ->{
			Connection con=null;
			PreparedStatement psmt=null;
			Statement smt=null;
			ResultSet rs=null;
			int DMLStatus=0;
			try {
					Driver.class.forName(BaseClass.getProps().getProperty("MYSQL_JDBC_SERVER"));
					con=DriverManager.getConnection(BaseClass.getProps().getProperty("MYSQL_JDBC_URL"),BaseClass.getProps().getProperty("DB_USERNAME"),BaseClass.getProps().getProperty("DB_PWD"));
					con.setAutoCommit(false);
					String tempQuery=sqlQuery.split(" ")[0];
					switch(tempQuery.toLowerCase()){
					case "select":
						smt=con.createStatement();
						rs=smt.executeQuery(sqlQuery);
						return rs;
					case "insert":
						psmt=con.prepareStatement(sqlQuery);
						DMLStatus=psmt.executeUpdate();
						return DMLStatus;
					case "update":
						psmt=con.prepareStatement(sqlQuery);
						DMLStatus=psmt.executeUpdate();
						return DMLStatus;
					case "delete":
						psmt=con.prepareStatement(sqlQuery);
						DMLStatus=psmt.executeUpdate();
						return DMLStatus;
					default:
						throw new SQLException("SQL Command not matched any pre-defined operations.");
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		 return null;
	};

}
