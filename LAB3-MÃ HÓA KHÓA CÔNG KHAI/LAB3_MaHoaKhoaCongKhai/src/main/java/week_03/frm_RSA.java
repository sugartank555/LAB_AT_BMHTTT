package week_03;

import javax.swing.SwingUtilities;
public final class frm_RSA extends CryptoFrame {
 public frm_RSA(){super("RSA Cipher (OAEP SHA-256)","RSA",new RSACipher());}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_RSA().setVisible(true));}
}
