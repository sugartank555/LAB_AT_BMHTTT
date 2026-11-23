package week_03;

import java.awt.*;
import javax.swing.*;
/** Giao dien Swing dung chung cho RSA, ECC, RSA-AES; moi form la JFrame rieng. */
public class CryptoFrame extends JFrame {
 protected final JTextArea pub=new JTextArea(2,60),pri=new JTextArea(2,60),plain=new JTextArea(4,60),cipher=new JTextArea(5,60);
 protected final JLabel status=new JLabel("Hay Generate Keys truoc"),keyInfo=new JLabel("Chua co khoa");
 private final String algorithm;private final Object engine;
 protected final JComboBox<String> bits=new JComboBox<>(new String[]{"2048","3072"});
 public CryptoFrame(String label,String a,Object obj){algorithm=a;engine=obj;setTitle(label);setSize(810,760);setDefaultCloseOperation(DISPOSE_ON_CLOSE);setLocationRelativeTo(null);
 JPanel root=new JPanel(new BorderLayout(10,10));root.setBorder(BorderFactory.createEmptyBorder(14,20,14,20));JLabel title=new JLabel(label);title.setFont(new Font("Segoe UI",Font.BOLD,20));root.add(title,BorderLayout.NORTH);
 JPanel content=new JPanel();content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
 if(a.equals("RSA")){JPanel sel=new JPanel(new FlowLayout(FlowLayout.LEFT));sel.add(new JLabel("Key size:"));sel.add(bits);sel.add(keyInfo);content.add(sel);}else{content.add(keyInfo);}
 content.add(new JLabel("Public key (Base64):"));pub.setLineWrap(true);pub.setEditable(false);content.add(new JScrollPane(pub));
 content.add(new JLabel("Private key (Base64) - KHONG CHIA SE:"));pri.setLineWrap(true);pri.setEditable(false);content.add(new JScrollPane(pri));
 JPanel k=new JPanel(new FlowLayout(FlowLayout.LEFT));addButton(k,"Generate Keys",()->generate());addButton(k,"Save Keys",()->saveKeys());addButton(k,"Load Keys",()->loadKeys());content.add(k);
 content.add(new JLabel("Plaintext:"));plain.setLineWrap(true);plain.setWrapStyleWord(true);content.add(new JScrollPane(plain));
 content.add(new JLabel("Ciphertext (Base64):"));cipher.setLineWrap(true);cipher.setWrapStyleWord(true);content.add(new JScrollPane(cipher));
 JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));addButton(actions,"Encrypt",()->encrypt());addButton(actions,"Decrypt",()->decrypt());if(a.equals("RSA-AES"))addButton(actions,"Sua 1 byte ban ma",()->tamper());content.add(actions);
 root.add(content,BorderLayout.CENTER);root.add(status,BorderLayout.SOUTH);setContentPane(root);
 }
 private void addButton(JPanel panel,String text,Runnable action){JButton b=new JButton(text);b.addActionListener(e->action.run());panel.add(b);}
 private void error(String operation,Exception e){status.setText(operation+" that bai: "+e.getMessage());JOptionPane.showMessageDialog(this,e.getMessage(),"Loi",JOptionPane.ERROR_MESSAGE);}
 private void generate(){try{long start=System.nanoTime();if(engine instanceof RSACipher o)o.generateKeys(Integer.parseInt((String)bits.getSelectedItem()));else if(engine instanceof ECCCipher o)o.generateKeys();else if(engine instanceof RSA_AES_Cipher o)o.generateKeys();showKeys();keyInfo.setText("Tao khoa xong ("+Math.round((System.nanoTime()-start)/1e6)+" ms)");status.setText("Da tao cap khoa moi");}catch(Exception e){error("Generate",e);}}
 private String publicKey(){if(engine instanceof RSACipher o)return o.getPublicKeyBase64();if(engine instanceof ECCCipher o)return o.getPublicKeyBase64();return ((RSA_AES_Cipher)engine).getPublicKeyBase64();}
 private String privateKey(){if(engine instanceof RSACipher o)return o.getPrivateKeyBase64();if(engine instanceof ECCCipher o)return o.getPrivateKeyBase64();return ((RSA_AES_Cipher)engine).getPrivateKeyBase64();}
 private void showKeys(){pub.setText(publicKey());pri.setText(privateKey());pub.setCaretPosition(0);pri.setCaretPosition(0);}
 private void saveKeys(){if(pub.getText().isEmpty()){JOptionPane.showMessageDialog(this,"Generate Keys truoc");return;}String p=FileTextIO.saveString(this,CryptoSupport.toKeyFile(pub.getText(),pri.getText()),algorithm.toLowerCase()+"_keys.txt");if(p!=null)status.setText("Da luu khoa - KHONG upload file private key len GitHub");}
 private void loadKeys(){String file=FileTextIO.openString(this);if(file==null)return;try{String[] keys=CryptoSupport.fromKeyFile(file);if(engine instanceof RSACipher o)o.loadKeys(keys[0],keys[1]);else if(engine instanceof ECCCipher o)o.loadKeys(keys[0],keys[1]);else ((RSA_AES_Cipher)engine).loadKeys(keys[0],keys[1]);showKeys();status.setText("Da nap khoa");}catch(Exception e){error("Load Keys",e);}}
 private String enc(String s)throws Exception{if(engine instanceof RSACipher o)return o.encrypt(s);if(engine instanceof ECCCipher o)return o.encrypt(s);return ((RSA_AES_Cipher)engine).encrypt(s);}
 private String dec(String s)throws Exception{if(engine instanceof RSACipher o)return o.decrypt(s);if(engine instanceof ECCCipher o)return o.decrypt(s);return ((RSA_AES_Cipher)engine).decrypt(s);}
 private void encrypt(){String s=plain.getText();if(s.isEmpty()){JOptionPane.showMessageDialog(this,"Nhap Plaintext");return;}try{String result=enc(s);cipher.setText(result);cipher.setCaretPosition(0);status.setText("Encrypt OK - "+result.length()+" ky tu Base64 "+HistoryDao.tryInsert(algorithm,"ENCRYPT",s.length(),true));}catch(Exception e){HistoryDao.tryInsert(algorithm,"ENCRYPT",s.length(),false);error("Encrypt",e);}}
 private void decrypt(){String s=cipher.getText();if(s.isBlank()){JOptionPane.showMessageDialog(this,"Nhap Ciphertext");return;}try{plain.setText(dec(s));plain.setCaretPosition(0);status.setText("Decrypt OK "+HistoryDao.tryInsert(algorithm,"DECRYPT",s.length(),true));}catch(Exception e){HistoryDao.tryInsert(algorithm,"DECRYPT",s.length(),false);error("Decrypt",e);}}
 private void tamper(){try{byte[] b=CryptoSupport.unb64(cipher.getText());if(b.length<1)throw new Exception("Chua co ciphertext");b[b.length-1]^=1;cipher.setText(CryptoSupport.b64(b));status.setText("Da sua 1 byte ban ma. Decrypt phai that bai.");}catch(Exception e){error("Tamper",e);}}
}
