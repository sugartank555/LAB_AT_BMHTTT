package week_03;

import java.awt.*;
import javax.swing.*;
public final class frm_Menu extends JFrame {
 public frm_Menu(){setTitle("LAB 3 - Ma hoa khoa cong khai");setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(740,360);setLocationRelativeTo(null);JPanel p=new JPanel(new BorderLayout(12,12));p.setBorder(BorderFactory.createEmptyBorder(20,22,20,22));JLabel title=new JLabel("LAB 3 - MA HOA KHOA CONG KHAI");title.setFont(new Font("Segoe UI",Font.BOLD,22));p.add(title,BorderLayout.NORTH);JPanel buttons=new JPanel(new GridLayout(2,3,12,12));addButton(buttons,"3.2 RSA (Swing)",()->new frm_RSA().setVisible(true));addButton(buttons,"3.3 ECC",()->new frm_ECC().setVisible(true));addButton(buttons,"3.4 RSA + AES",()->new frm_RSA_AES().setVisible(true));addButton(buttons,"3.5 Chu ky ECDSA",()->new frm_ECC_Signature().setVisible(true));addButton(buttons,"Ket noi SQL Server",()->new frm_KetNoiSQL().setVisible(true));addButton(buttons,"Xem lich su",()->new frm_LichSu().setVisible(true));p.add(buttons,BorderLayout.CENTER);p.add(new JLabel("Bai 3.1 RSA Console: Run As > Java Application > RSADemo.java"),BorderLayout.SOUTH);add(p);}
 private void addButton(JPanel p,String label,Runnable run){JButton b=new JButton(label);b.addActionListener(e->run.run());p.add(b);}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_Menu().setVisible(true));}
}
