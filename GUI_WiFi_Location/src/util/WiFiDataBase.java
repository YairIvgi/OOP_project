package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import genral.WifiSpot;

public class WiFiDataBase {
	 private static String _ip = "127.0.0.1";
	  private static String _url = "jdbc:mysql://"+_ip+":3306/oop_course_ariel";
	  private static String _user = "root";
	  private static String _password = "idan1994";
	  private static Connection _con = null;
     
   public static void main(String[] args) {
	   test_101();
   }
   public static int test_101() {
       Statement st = null;
       ResultSet rs = null;
       int max_id = -1;
       //String ip = "localhost";
      // String ip = "192.168.1.18";

       try {     
           _con = DriverManager.getConnection(_url, _user, _password);
           st = _con.createStatement();
           rs = st.executeQuery("SELECT VERSION()");
           if (rs.next()) {
               rs.getString(1);
           }
          
           PreparedStatement pst = _con.prepareStatement("SELECT * FROM test101");
           rs = pst.executeQuery();
           
           while (rs.next()) {
           	int id = rs.getInt(1);
           	if(id>max_id) {max_id=id;}
               System.out.print(id);
               System.out.print(": ");
               System.out.print(rs.getString(2));
               System.out.print(" (");
               double lat = rs.getDouble(3);
               System.out.print(lat);
               System.out.print(", ");
               double lon = rs.getDouble(4);
               System.out.print(lon);
               System.out.println(") ");
           }
       } catch (SQLException ex) {
           Logger lgr = Logger.getLogger(WiFiDataBase.class.getName());
           lgr.log(Level.SEVERE, ex.getMessage(), ex);
       } finally {
           try {
               if (rs != null) {rs.close();}
               if (st != null) { st.close(); }
               if (_con != null) { _con.close();  }
           } catch (SQLException ex) {
               
               Logger lgr = Logger.getLogger(WiFiDataBase.class.getName());
               lgr.log(Level.WARNING, ex.getMessage(), ex);
           }
       }
       return max_id;
   }
   

   
  
}
