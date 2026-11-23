package week_03;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
public final class HistoryDao {
 private HistoryDao(){}
 public static void insert(String algorithm,String op,int inputLength,boolean success)throws SQLException{try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("INSERT INTO dbo.History(Algorithm,Operation,InputLength,Success) VALUES(?,?,?,?)")){p.setString(1,algorithm);p.setString(2,op);p.setInt(3,inputLength);p.setBoolean(4,success);p.executeUpdate();}}
 public static String tryInsert(String algorithm,String op,int len,boolean success){if(!DbConfig.isReady())return "(Chua ket noi SQL - khong luu lich su)";try{insert(algorithm,op,len,success);return "(Da luu SQL)";}catch(SQLException e){return "(Loi SQL: "+e.getMessage()+")";}}
 public static DefaultTableModel emptyModel(){return new DefaultTableModel(new Object[]{"Id","Thoi gian","Thuat toan","Thao tac","Do dai vao","Thanh cong"},0){public boolean isCellEditable(int r,int c){return false;}};}
 public static DefaultTableModel loadLatest(int count)throws SQLException{DefaultTableModel t=emptyModel();try(Connection c=DbConfig.open();PreparedStatement p=c.prepareStatement("SELECT TOP (?) Id,CreatedAt,Algorithm,Operation,InputLength,Success FROM dbo.History ORDER BY Id DESC")){p.setInt(1,count);try(ResultSet rs=p.executeQuery()){while(rs.next())t.addRow(new Object[]{rs.getLong(1),rs.getTimestamp(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getBoolean(6)?"Co":"Khong"});}}return t;}
}
