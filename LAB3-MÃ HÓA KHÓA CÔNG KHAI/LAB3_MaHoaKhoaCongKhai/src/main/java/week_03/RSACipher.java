package week_03;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import javax.crypto.Cipher;
import javax.crypto.spec.*;
public final class RSACipher {
 private static final OAEPParameterSpec OAEP=new OAEPParameterSpec("SHA-256","MGF1",MGF1ParameterSpec.SHA256,PSource.PSpecified.DEFAULT);
 private PublicKey pub;private PrivateKey priv;private int bits;
 public void generateKeys(int size)throws Exception{if(size!=2048&&size!=3072)throw new IllegalArgumentException("Chon 2048 hoac 3072");KeyPairGenerator g=KeyPairGenerator.getInstance("RSA");g.initialize(size);KeyPair kp=g.generateKeyPair();pub=kp.getPublic();priv=kp.getPrivate();bits=size;}
 public int maxPlaintextBytes(){return bits/8-66;}
 public int getBits(){return bits;}
 public boolean hasKeys(){return pub!=null&&priv!=null;}
 public String encrypt(String s)throws Exception{if(pub==null)throw new IllegalStateException("Hay Generate Keys truoc");byte[] bytes=CryptoSupport.utf8(s);if(bytes.length>maxPlaintextBytes())throw new IllegalArgumentException("Vuot gioi han "+maxPlaintextBytes()+" byte; hay dung RSA-AES");Cipher c=Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");c.init(Cipher.ENCRYPT_MODE,pub,OAEP);return CryptoSupport.b64(c.doFinal(bytes));}
 public String decrypt(String s)throws Exception{if(priv==null)throw new IllegalStateException("Thieu private key");Cipher c=Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");c.init(Cipher.DECRYPT_MODE,priv,OAEP);return CryptoSupport.text(c.doFinal(CryptoSupport.unb64(s)));}
 public String getPublicKeyBase64(){return CryptoSupport.b64(pub.getEncoded());}
 public String getPrivateKeyBase64(){return priv==null?"":CryptoSupport.b64(priv.getEncoded());}
 public void loadKeys(String p,String q)throws Exception{KeyFactory k=KeyFactory.getInstance("RSA");pub=k.generatePublic(new X509EncodedKeySpec(CryptoSupport.unb64(p)));priv=q.isBlank()?null:k.generatePrivate(new PKCS8EncodedKeySpec(CryptoSupport.unb64(q)));bits=((RSAPublicKey)pub).getModulus().bitLength();}
}
