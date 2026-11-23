package week_03;

import java.awt.*;
import javax.swing.*;
public final class frm_ECC_Signature extends JFrame {
 private final ECCSignature ecdsa=new ECCSignature();
 private final JTextArea pub=new JTextArea(2,55),pri=new JTextArea(2,55),message=new JTextArea(4,55),signature=new JTextArea(4,55);
 private final JLabel result=new JLabel(""),status=new JLabel("Hay Generate Keys truoc");
 public frm_ECC_Signature(){setTitle("ECDSA - SHA256withECDSA");setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(800,690);setLocationRelativeTo(null);JPanel p=new JPanel();p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));p.setBorder(BorderFactory.createEmptyBorder(12,16,12,16));JLabel h=new JLabel("CHU KY SO ECDSA (SHA256withECDSA)");h.setFont(new Font("Segoe UI",Font.BOLD,20));p.add(h);
 p.add(new JLabel("Public key:"));pub.setLineWrap(true);pub.setEditable(false);p.add(new JScrollPane(pub));p.add(new JLabel("Private key (GIU BI MAT):"));pri.setLineWrap(true);pri.setEditable(false);p.add(new JScrollPane(pri));
 JPanel row=new JPanel(new FlowLayout(FlowLayout.LEFT));button(row,"Generate Keys",()->generate());button(row,"Save Keys",()->save());button(row,"Load Keys",()->load());p.add(row);
 p.add(new JLabel("Message:"));message.setText("THONG DIEP A");p.add(new JScrollPane(message));p.add(new JLabel("Signature (Base64):"));signature.setLineWrap(true);p.add(new JScrollPane(signature));
 JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));button(actions,"Sign",()->sign());button(actions,"Verify",()->verify());p.add(actions);result.setFont(new Font("Segoe UI",Font.BOLD,24));p.add(result);p.add(status);add(p);}
 private void button(JPanel row,String name,Runnable run){JButton b=new JButton(name);b.addActionListener(e->run.run());row.add(b);}
 private void error(Exception e){status.setText("Loi: "+e.getMessage());JOptionPane.showMessageDialog(this,e.getMessage(),"Loi",JOptionPane.ERROR_MESSAGE);}
 private void showKeys(){pub.setText(ecdsa.getPublicKeyBase64());pri.setText(ecdsa.getPrivateKeyBase64());pub.setCaretPosition(0);pri.setCaretPosition(0);}
 private void generate(){try{ecdsa.generateKeys();showKeys();status.setText("Da tao cap khoa ECDSA");result.setText("");}catch(Exception e){error(e);}}
 private void save(){if(pub.getText().isBlank()){JOptionPane.showMessageDialog(this,"Generate Keys truoc");return;}String path=FileTextIO.saveString(this,CryptoSupport.toKeyFile(pub.getText(),pri.getText()),"ecdsa_keys.txt");if(path!=null)status.setText("Da luu - giu bi mat private key");}
 private void load(){String s=FileTextIO.openString(this);if(s==null)return;try{String[] keys=CryptoSupport.fromKeyFile(s);ecdsa.loadKeys(keys[0],keys[1]);showKeys();status.setText("Da nap khoa");}catch(Exception e){error(e);}}
 private void sign(){try{signature.setText(ecdsa.sign(message.getText()));result.setText("");status.setText("Ky thanh cong "+HistoryDao.tryInsert("ECDSA","SIGN",message.getText().length(),true));}catch(Exception e){HistoryDao.tryInsert("ECDSA","SIGN",message.getText().length(),false);error(e);}}
 private void verify(){try{boolean valid=ecdsa.verify(message.getText(),signature.getText());result.setText(valid?"VALID":"INVALID");result.setForeground(valid?new Color(0,120,50):Color.RED);status.setText("Xac minh: "+(valid?"VALID":"INVALID")+" "+HistoryDao.tryInsert("ECDSA","VERIFY",message.getText().length(),valid));}catch(Exception e){HistoryDao.tryInsert("ECDSA","VERIFY",message.getText().length(),false);error(e);}}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_ECC_Signature().setVisible(true));}
}
