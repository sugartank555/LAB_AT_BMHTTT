package week_03;

import java.sql.*;
import java.util.Arrays;
public final class DbConfig {
 private static String host,db,username;private static int port;private static char[] pw;
 private DbConfig(){}
 public static synchronized void set(String h,int p,String database,String user,char[] password){if(h.isBlank()||database.isBlank()||user.isBlank()||p<1||p>65535)throw new IllegalArgumentException("Cau hinh SQL khong hop le");clear();host=h;port=p;db=database;username=user;pw=password.clone();}
 public static synchronized void clear(){host=null;if(pw!=null)Arrays.fill(pw,'\0');pw=null;}
 public static synchronized boolean isReady(){return host!=null;}
 public static synchronized Connection open()throws SQLException{if(!isReady())throw new SQLException("Hay Test Connection truoc");String url="jdbc:sqlserver://"+host+":"+port+";databaseName="+db+";encrypt=true;trustServerCertificate=true;loginTimeout=5";return DriverManager.getConnection(url,username,new String(pw));}
 public static String test()throws SQLException{try(Connection c=open()){return "Ket noi thanh cong: "+c.getMetaData().getDatabaseProductName()+" - "+c.getCatalog();}}
}
