package week_03;

import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import javax.crypto.spec.*;
public final class RSA_AES_Cipher {
 private static final OAEPParameterSpec OAEP=new OAEPParameterSpec("SHA-256","MGF1",MGF1ParameterSpec.SHA256,PSource.PSpecified.DEFAULT);
 private PublicKey pub;private PrivateKey priv;
 public void generateKeys()throws Exception{KeyPairGenerator g=KeyPairGenerator.getInstance("RSA");g.initialize(2048);KeyPair p=g.generateKeyPair();pub=p.getPublic();priv=p.getPrivate();}
 public String encrypt(String s)throws Exception{if(pub==null)throw new IllegalStateException("Generate Keys truoc");byte[] key=CryptoSupport.randomBytes(32),nonce=CryptoSupport.randomBytes(12);byte[] encrypted=CryptoSupport.gcmEncrypt(key,nonce,CryptoSupport.utf8(s));Cipher c=Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");c.init(Cipher.ENCRYPT_MODE,pub,OAEP);return CryptoSupport.b64(CryptoSupport.concat(c.doFinal(key),nonce,encrypted));}
 public String decrypt(String s)throws Exception{if(priv==null)throw new IllegalStateException("Thieu private key");byte[] all=CryptoSupport.unb64(s);byte[] wrapped=CryptoSupport.slice(all,0,256),nonce=CryptoSupport.slice(all,256,268),payload=CryptoSupport.slice(all,268,all.length);Cipher c=Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");c.init(Cipher.DECRYPT_MODE,priv,OAEP);byte[] key=c.doFinal(wrapped);return CryptoSupport.text(CryptoSupport.gcmDecrypt(key,nonce,payload));}
 public String getPublicKeyBase64(){return CryptoSupport.b64(pub.getEncoded());}public String getPrivateKeyBase64(){return priv==null?"":CryptoSupport.b64(priv.getEncoded());}
 public void loadKeys(String p,String q)throws Exception{KeyFactory k=KeyFactory.getInstance("RSA");pub=k.generatePublic(new X509EncodedKeySpec(CryptoSupport.unb64(p)));priv=q.isBlank()?null:k.generatePrivate(new PKCS8EncodedKeySpec(CryptoSupport.unb64(q)));}
}
