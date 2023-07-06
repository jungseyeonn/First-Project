package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	//db에 추가
	public void insert(String id, String email, String pwd, String div) {
		try {
			Class.forName(driver);
//			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, password);
//			System.out.println("oracle connection success.");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			System.out.println("statement create success.");

			String sql = "insert into Login values ('" + id + "','" + pwd + "','" + email + "','" + div + "')";

			boolean b = stmt.execute(sql);
			if (!b) {
				System.out.println("Insert success.\n");
			} else {
				System.out.println("Insert fail.\n");
			}

			String sql2 = "SELECT SID, SPWD, SEMAIL, UDIV FROM LOGIN";
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				System.out.print(rs.getString("SID") + "\t");
				System.out.print(rs.getString("SEMAIL") + "\t");
				System.out.print(rs.getString("SPWD") + "\t");
				System.out.println(rs.getString("UDIV") + " ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//조회 쿼리
	public ArrayList<LoginVo> list(String id, String div) {
		ArrayList<LoginVo> list = new ArrayList<LoginVo>();

		try {
			connDB();

			String query = "SELECT * FROM Login";
			if (id != null) {
				query += " where sid ='" + id + "' and udiv = '" + div + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
			} else {
				System.out.println(rs.getRow() + " rows selected...");
				rs.previous();

				while (rs.next()) {
					String uid = rs.getString("SID");
					String upwd = rs.getString("SPWD");

					LoginVo data = new LoginVo(uid, upwd);
					list.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	
	//비밀번호 찾기 id, email 조회
	public ArrayList<FoundPVo> listpwd(String id, String email) {
		ArrayList<FoundPVo> listpwd = new ArrayList<FoundPVo>();	
		
		try {
			connDB();

			String query = "SELECT SID, SEMAIL FROM Login";
			if (id != null) {
				query += " where sid = '" + id + "'";
			}

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
			} else {
				System.out.println(rs.getRow() + " rows selected...");
				rs.previous();

				while (rs.next()) {
					String uid = rs.getString("SID");
					String ue = rs.getString("SEMAIL");

					FoundPVo data = new FoundPVo(uid, ue);
					listpwd.add(data);
					
					System.out.print(rs.getString("SID") + "\t");
					System.out.println(rs.getString("SEMAIL") + " ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listpwd;
	}
	
	
	//비밀번호 찾기 - 변경
	public ArrayList<FoundPVo> re(String id, String pwd) {
		ArrayList<FoundPVo> re = new ArrayList<FoundPVo>();	
		int res = 0;
		
		try {			
			connDB();
			String sql = "update Login set spwd = '" + pwd + "'";
			System.out.println(sql);
			if (id != null) {
				sql += " where sid = '" + id + "'";
			}
					
			res = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return re;
	}

	public void connDB() {
		try {
			Class.forName(driver);
//			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, password);
//			System.out.println("oracle connection success.");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			System.out.println("statement create success.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
