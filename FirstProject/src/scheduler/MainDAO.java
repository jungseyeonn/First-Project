package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MainDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "c##firstproj";
	String password = "firstproj";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public ArrayList<MainVo> chosc(String div) {
		ArrayList<MainVo> chosc = new ArrayList<MainVo>();

		// 콤보박스에 학교이름 넣기
		try {
			connDB();
			String query = "SELECT INAME FROM SINFO";

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
			} else {
				System.out.println(rs.getRow() + " rows selected...");
				rs.beforeFirst();

				while (rs.next()) {
					String iname = rs.getString("INAME");

					MainVo data = new MainVo(iname);
					chosc.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chosc;
	}

	// 캘린더 - DB 연결
	public ArrayList<MainVo> listca(int year, int month, int day) {
		ArrayList<MainVo> listca = new ArrayList<MainVo>();

		try {
			connDB();

			String query = "SELECT SNAME, SCHEDULE, TO_CHAR(SDAY, 'YYYY'), TO_NUMBER(TO_CHAR(SDAY, 'MM')), TO_NUMBER(TO_CHAR(SDAY, 'DD')) FROM SCHOOL";
			if (day != 0) {
				query += " where TO_CHAR(SDAY, 'YYYY')='" + year + "' AND TO_NUMBER(TO_CHAR(SDAY, 'MM'))='" + month
						+ "' AND TO_NUMBER(TO_CHAR(SDAY, 'DD'))='" + day + "'";
			}

			System.out.println(query);

			rs = stmt.executeQuery(query);
			rs.last();

			if (rs.getRow() == 0) {
				System.out.println("0 row selected...");
			} else {
				System.out.println(rs.getRow() + " rows selected...");
				rs.beforeFirst();

				while (rs.next()) {
					String name = rs.getString("SNAME");
					String sc = rs.getString("SCHEDULE");
					System.out.println(name + " " + sc + " ");

					MainVo data = new MainVo(name, sc, year, month, day);
					listca.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listca;
	}

	// 추가
	public void insert(String div, String name, String schedule, int year, int month, int day) {
		try {
			connDB();
			String sql = "insert into School (SDIV,SNAME, SCHEDULE, SDAY) values ('" + div + "','" + name + "','"
					+ schedule + "'," + "TO_DATE('" + year + "-" + month + "-" + day + "', 'YYYY-MM-DD'))";
			System.out.println(sql);
			boolean b = stmt.execute(sql);
			if (!b) {
				System.out.println("Insert success.\n");
			} else {
				System.out.println("Insert fail.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 삭제
	public int delete(String div, String name, String schedule, int year, int month, int day) {

		try {
			connDB();

			String sqldb = "SELECT SDIV , SNAME, SCHEDULE, to_char(SDAY, 'yyyy'), TO_NUMBER(TO_CHAR(SDAY, 'MM')), TO_NUMBER(TO_CHAR(SDAY, 'DD')) FROM SCHOOL";

			if (div != null) {
				sqldb += " WHERE Sdiv ='" + div + "'and sNAME = '" + name + "' and schedule = '" + schedule
						+ "' and to_char(SDAY, 'yyyy') = '" + year + "' and TO_NUMBER(TO_CHAR(SDAY, 'MM')) = '" + month + "' and TO_NUMBER(TO_CHAR(SDAY, 'DD')) = '" + day+ "'";
			}

			System.out.println(sqldb);

			rs = stmt.executeQuery(sqldb);
			rs.last();
			
			if (rs.getRow() == 0) {
				System.out.println("Select fail.\n");
				return 0;
			} else {
				System.out.println("Select success.\n");
				rs.previous();
			
				while (rs.next()) {
					String sdiv = rs.getString("sdiv");
					String sname = rs.getString("sname");
					String sc = rs.getString("schedule");
				}
			}

			String sql = "delete from School";
			if (div != null) {
				sql += " where sdiv = '" + div + "' AND sname = '" + name + "' AND schedule ='" + schedule
						+ "' AND sday =" + "TO_DATE('" + year + "-" + month + "-" + day + "','YYYY-MM-DD')";
			}
			
			System.out.println(sql);			
			rs = stmt.executeQuery(sql);
			boolean b = stmt.execute(sql);
			if (!b) {
				System.out.println("Delete success.\n");
			} else {
				System.out.println("Delete fail.\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return 1;
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
