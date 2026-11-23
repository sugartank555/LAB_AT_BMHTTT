package week_03;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.*;
public final class FileTextIO {
 private FileTextIO(){}
 public static String openString(JFrame owner){JFileChooser fc=new JFileChooser();if(fc.showOpenDialog(owner)!=JFileChooser.APPROVE_OPTION)return null;try{return Files.readString(fc.getSelectedFile().toPath(),StandardCharsets.UTF_8);}catch(Exception e){JOptionPane.showMessageDialog(owner,e.getMessage());return null;}}
 public static String saveString(JFrame owner,String content,String defaultName){JFileChooser fc=new JFileChooser();fc.setSelectedFile(new File(defaultName));if(fc.showSaveDialog(owner)!=JFileChooser.APPROVE_OPTION)return null;File f=fc.getSelectedFile();if(!f.getName().toLowerCase().endsWith(".txt"))f=new File(f.getParentFile(),f.getName()+".txt");if(f.exists()&&JOptionPane.showConfirmDialog(owner,"Ghi de tep da ton tai?","Xac nhan",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return null;try{Files.writeString(f.toPath(),content,StandardCharsets.UTF_8);return f.getAbsolutePath();}catch(Exception e){JOptionPane.showMessageDialog(owner,e.getMessage());return null;}}
 public static String openText(JFrame parent,JTextArea target){String s=openString(parent);if(s!=null)target.setText(s);return s;}
 public static String saveText(JFrame parent,JTextArea target,String name){return saveString(parent,target.getText(),name);}
}
