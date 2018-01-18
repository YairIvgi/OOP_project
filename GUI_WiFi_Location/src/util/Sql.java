package util;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import readAndWrite.WriteCsv;

public class Sql {
	
	private static String m_ip;// = "5.29.193.52";
	private static String m_url;
	private static String m_user;// = "oop1";
	private static String m_password;// = "Lambda1();";
	private static String m_table;//	="ex4_db";
	private static Connection _con = null;

	public Sql(String ip,String port,String folder,String user,String password,String table){
		//port = "3306";
		//folder = "oop_course_ariel";
		m_ip = ip;
		m_user = user;
		m_password = password;
		m_table = table;
		m_url = "jdbc:mysql://"+m_ip+":"+port+"/"+folder;
	}

	//	private static String _ip = "127.0.0.1";
	//	private static String _url = "jdbc:mysql://"+_ip+":3306/oop_course_ariel";
	//	private static String _user = "root";
	//	private static String _password = "???";
	//	private static Connection _con = null;

	@SuppressWarnings("resource")
	public int getSqlTable() throws Exception {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;

		try {     
			_con = DriverManager.getConnection(m_url, m_user, m_password);
			st = _con.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
			if (rs.next()) {
				rs.getString(1);
			}

			PreparedStatement pst = _con.prepareStatement("SELECT * FROM "+m_table);
			rs = pst.executeQuery();
			List<String> data = new ArrayList<String>();
			while (rs.next()) {
				String line;
				int numOfSampels = Integer.parseInt(rs.getString("number_of_ap"));

				line = rs.getString("time");
				line += ","+rs.getString("device");
				line += ","+rs.getString("lat");
				line += ","+rs.getString("lon");
				line += ","+rs.getString("alt");
				line += ","+String.valueOf(numOfSampels);

				for (int i = 0; i < numOfSampels; i++) {
					line+=",,"+rs.getString("mac"+i);				//"SSID+MAC"
					line+=",,"+rs.getString("rssi"+i);				//"Frequency+RSSI"		
				}
				if(numOfSampels<10){
					for (int i = numOfSampels; i < 10; i++) {
						line+=",,,";
					}
				}
				data.add(line);
			}
			String outputPath = System.getProperty("user.dir");
			outputPath += "\\sqlData.csv";
			WriteCsv wc =new WriteCsv(outputPath);
			wc.dBFormatFromLine(data);
			wc.close();
		} catch (SQLException ex) {
		//	Logger lgr = Logger.getLogger(Sql.class.getName());
			throw new SQLException();
	//		lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (st != null) { st.close(); }
				if (_con != null) { _con.close();  }
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(Sql.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return max_id;
	}

	public static String getM_ip() {
		return m_ip;
	}

	public static String getM_url() {
		return m_url;
	}

	public static String getM_user() {
		return m_user;
	}

	public static String getM_password() {
		return m_password;
	}

	public static String getM_table() {
		return m_table;
	}
	
}