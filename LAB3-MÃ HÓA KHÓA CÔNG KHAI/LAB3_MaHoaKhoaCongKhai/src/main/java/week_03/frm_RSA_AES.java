package week_03;

import javax.swing.SwingUtilities;
public final class frm_RSA_AES extends CryptoFrame {
 public frm_RSA_AES(){super("RSA + AES Cipher (Hybrid)","RSA-AES",new RSA_AES_Cipher());}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_RSA_AES().setVisible(true));}
}
