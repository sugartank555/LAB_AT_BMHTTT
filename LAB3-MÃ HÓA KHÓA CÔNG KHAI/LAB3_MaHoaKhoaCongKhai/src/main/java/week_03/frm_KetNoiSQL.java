package week_03;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
public final class frm_KetNoiSQL extends JFrame {
 private final JTextField host=new JTextField("127.0.0.1"),port=new JTextField("14333"),db=new JTextField("LAB3_MaHoaKhoaCongKhai"),user=new JTextField("lab3_user");
 private final JPasswordField password=new JPasswordField();private final JLabel status=new JLabel("Chua ket noi");
 public frm_KetNoiSQL(){setTitle("Ket noi Microsoft SQL Server");setSize(590,410);setLocationRelativeTo(null);setDefaultCloseOperation(DISPOSE_ON_CLOSE);JPanel root=new JPanel(new BorderLayout(8,8));root.setBorder(BorderFactory.createEmptyBorder(15,18,15,18));JLabel title=new JLabel("KET NOI MICROSOFT SQL SERVER");title.setFont(new Font("Segoe UI",Font.BOLD,20));root.add(title,BorderLayout.NORTH);JPanel fields=new JPanel(new GridLayout(5,2,8,8));fields.add(new JLabel("Server:"));fields.add(host);fields.add(new JLabel("Port:"));fields.add(port);fields.add(new JLabel("Database:"));fields.add(db);fields.add(new JLabel("User:"));fields.add(user);fields.add(new JLabel("Password:"));fields.add(password);root.add(fields,BorderLayout.CENTER);JPanel foot=new JPanel(new BorderLayout());JPanel buttons=new JPanel();JButton test=new JButton("Test Connection"),hist=new JButton("Xem lich su");buttons.add(test);buttons.add(hist);foot.add(buttons,BorderLayout.NORTH);foot.add(status,BorderLayout.SOUTH);root.add(foot,BorderLayout.SOUTH);test.addActionListener(e->{char[] pw=password.getPassword();try{DbConfig.set(host.getText().trim(),Integer.parseInt(port.getText().trim()),db.getText().trim(),user.getText().trim(),pw);status.setText(DbConfig.test());}catch(Exception ex){DbConfig.clear();status.setText("Ket noi that bai");JOptionPane.showMessageDialog(this,ex.getMessage());}finally{Arrays.fill(pw,'\0');}});hist.addActionListener(e->new frm_LichSu().setVisible(true));add(root);}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_KetNoiSQL().setVisible(true));}
}
