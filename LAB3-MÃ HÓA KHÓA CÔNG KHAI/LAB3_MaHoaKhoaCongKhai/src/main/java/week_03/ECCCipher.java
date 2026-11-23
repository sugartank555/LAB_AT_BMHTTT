package week_03;

import java.security.*;
import java.security.spec.*;
import javax.crypto.KeyAgreement;
public final class ECCCipher {
 private static final byte[] INFO=CryptoSupport.utf8("LAB3-ECC-AESGCM");
 private PublicKey pub;private PrivateKey priv;
 private static KeyPair pair()throws Exception{KeyPairGenerator g=KeyPairGenerator.getInstance("EC");g.initialize(new ECGenParameterSpec("secp256r1"));return g.generateKeyPair();}
 public void generateKeys()throws Exception{KeyPair p=pair();pub=p.getPublic();priv=p.getPrivate();}
 public String encrypt(String s)throws Exception{if(pub==null)throw new IllegalStateException("Generate Keys truoc");KeyPair ephemeral=pair();KeyAgreement ka=KeyAgreement.getInstance("ECDH");ka.init(ephemeral.getPrivate());ka.doPhase(pub,true);byte[] salt=CryptoSupport.randomBytes(16),nonce=CryptoSupport.randomBytes(12);byte[] k=CryptoSupport.hkdf(ka.generateSecret(),salt,INFO);byte[] out=CryptoSupport.gcmEncrypt(k,nonce,CryptoSupport.utf8(s));byte[] e=ephemeral.getPublic().getEncoded();return CryptoSupport.b64(CryptoSupport.concat(e,salt,nonce,out));}
 public String decrypt(String s)throws Exception{if(priv==null)throw new IllegalStateException("Thieu private key");byte[] bytes=CryptoSupport.unb64(s);int eph=91;PublicKey temp=KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(CryptoSupport.slice(bytes,0,eph)));byte[] salt=CryptoSupport.slice(bytes,eph,eph+16),nonce=CryptoSupport.slice(bytes,eph+16,eph+28),body=CryptoSupport.slice(bytes,eph+28,bytes.length);KeyAgreement ka=KeyAgreement.getInstance("ECDH");ka.init(priv);ka.doPhase(temp,true);return CryptoSupport.text(CryptoSupport.gcmDecrypt(CryptoSupport.hkdf(ka.generateSecret(),salt,INFO),nonce,body));}
 public String getPublicKeyBase64(){return CryptoSupport.b64(pub.getEncoded());}public String getPrivateKeyBase64(){return priv==null?"":CryptoSupport.b64(priv.getEncoded());}
 public void loadKeys(String p,String q)throws Exception{KeyFactory k=KeyFactory.getInstance("EC");pub=k.generatePublic(new X509EncodedKeySpec(CryptoSupport.unb64(p)));priv=q.isBlank()?null:k.generatePrivate(new PKCS8EncodedKeySpec(CryptoSupport.unb64(q)));}
}
