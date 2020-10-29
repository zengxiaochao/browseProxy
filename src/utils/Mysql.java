package utils;

import entity.IPBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mysql {
    public static void main(String args[]) {
    	  IPBean ipBean = new IPBean("202.168.116.110",1010,"Http");
    	  new Mysql().insertIp(ipBean);
    }
    public List<IPBean> getList()
    {
    	  List<IPBean> list = new ArrayList<IPBean>();
    	  try {
    		  Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序
    		  Connection connect = DriverManager.getConnection(
    				  "jdbc:mysql://localhost:3306/browseproxy?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");
    		  Statement statement = connect.createStatement();
    		  ResultSet resultSet = statement.executeQuery("select * from proxy");
    		  while (resultSet.next()) {
     		    int id= resultSet.getInt(1);
    		  	String ip= resultSet.getString(2);
				int port= resultSet.getInt(3);
    		  	String typ= resultSet.getString(4);
				String loc= resultSet.getString(5);
		  		IPBean ipBean = new IPBean(ip,port,typ);
		  		ipBean.setLoc(loc);
		  		ipBean.setId(id);
		  		list.add(ipBean);
    		  }
	      }
	      catch (Exception e) {
	      }
	      return list;
    }
    public void insertIp(IPBean ipBean)
    {
  	  List<IPBean> list = new ArrayList<IPBean>();
  	  try {
  		  Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序
  		  Connection connect = DriverManager.getConnection(
  				  "jdbc:mysql://localhost:3306/browseproxy?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");

  		  String sql = "INSERT INTO proxy (ip,port,typ,loc) VALUES(?,?,?,?)";		//插入sql语句
  		  try {
  			  PreparedStatement ps = connect.prepareStatement(sql);
  			  ps.setString(1, ipBean.getIp());
  			  ps.setInt(2, ipBean.getPort());
  			  ps.setString(3, ipBean.getType());
  			  ps.setString(4, ipBean.getLoc());

  			  ps.executeUpdate();			//执行sql语句
  		  } catch (SQLException e) {
			  System.out.println("IP重复："+e);
			  connect.close();
		  }finally {
  			  connect.close();
  		  }
  	  }
  	  catch (Exception e) {
  	  }
    }
	public static void Delete(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/browseproxy?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","123456");
			String sql = "DELETE FROM proxy WHERE id=?";		//sql语句

			PreparedStatement ps = connect.prepareStatement(sql);

			ps.setInt(1, id);
			ps.executeUpdate();		//执行sql语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
 
 