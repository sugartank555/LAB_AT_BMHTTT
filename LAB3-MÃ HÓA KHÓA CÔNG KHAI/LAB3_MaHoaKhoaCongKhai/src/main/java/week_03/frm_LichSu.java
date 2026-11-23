package week_03;

import java.awt.*;
import javax.swing.*;
public final class frm_LichSu extends JFrame {
 private final JTable table=new JTable(HistoryDao.emptyModel());private final JLabel status=new JLabel("Chua tai");
 public frm_LichSu(){setTitle("Lich su - dbo.History");setSize(940,510);setLocationRelativeTo(null);setDefaultCloseOperation(DISPOSE_ON_CLOSE);JPanel root=new JPanel(new BorderLayout(8,8));root.setBorder(BorderFactory.createEmptyBorder(12,15,12,15));JLabel title=new JLabel("LICH SU THAO TAC - DBO.HISTORY");title.setFont(new Font("Segoe UI",Font.BOLD,20));root.add(title,BorderLayout.NORTH);root.add(new JScrollPane(table),BorderLayout.CENTER);JPanel bottom=new JPanel(new BorderLayout());JButton reload=new JButton("Tai lai");reload.addActionListener(e->loadData());bottom.add(reload,BorderLayout.WEST);bottom.add(status,BorderLayout.CENTER);root.add(bottom,BorderLayout.SOUTH);add(root);loadData();}
 private void loadData(){try{table.setModel(HistoryDao.loadLatest(50));status.setText("  Da tai "+table.getRowCount()+" ban ghi");}catch(Exception e){status.setText("  Chua doc duoc lich su: "+e.getMessage());}}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_LichSu().setVisible(true));}
}
