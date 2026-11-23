package week_03;

import javax.swing.SwingUtilities;
public final class frm_ECC extends CryptoFrame {
 public frm_ECC(){super("ECC Cipher (P-256, ECDH + AES-GCM)","ECC",new ECCCipher());}
 public static void main(String[] args){SwingUtilities.invokeLater(()->new frm_ECC().setVisible(true));}
}
