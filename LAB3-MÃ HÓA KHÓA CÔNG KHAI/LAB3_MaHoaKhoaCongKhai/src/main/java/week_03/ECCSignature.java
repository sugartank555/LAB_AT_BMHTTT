package week_03;

import java.security.*;
import java.security.spec.*;
public final class ECCSignature {
 private PublicKey pub;private PrivateKey priv;
 public void generateKeys()throws Exception{KeyPairGenerator g=KeyPairGenerator.getInstance("EC");g.initialize(new ECGenParameterSpec("secp256r1"));KeyPair p=g.generateKeyPair();pub=p.getPublic();priv=p.getPrivate();}
 public String sign(String s)throws Exception{if(priv==null)throw new IllegalStateException("Thieu private key");Signature sig=Signature.getInstance("SHA256withECDSA");sig.initSign(priv);sig.update(CryptoSupport.utf8(s));return CryptoSupport.b64(sig.sign());}
 public boolean verify(String s,String signature)throws Exception{if(pub==null)throw new IllegalStateException("Thieu public key");Signature sig=Signature.getInstance("SHA256withECDSA");sig.initVerify(pub);sig.update(CryptoSupport.utf8(s));try{return sig.verify(CryptoSupport.unb64(signature));}catch(SignatureException|IllegalArgumentException e){return false;}}
 public String getPublicKeyBase64(){return CryptoSupport.b64(pub.getEncoded());}public String getPrivateKeyBase64(){return priv==null?"":CryptoSupport.b64(priv.getEncoded());}
 public void loadKeys(String p,String q)throws Exception{KeyFactory k=KeyFactory.getInstance("EC");pub=k.generatePublic(new X509EncodedKeySpec(CryptoSupport.unb64(p)));priv=q.isBlank()?null:k.generatePrivate(new PKCS8EncodedKeySpec(CryptoSupport.unb64(q)));}
}
