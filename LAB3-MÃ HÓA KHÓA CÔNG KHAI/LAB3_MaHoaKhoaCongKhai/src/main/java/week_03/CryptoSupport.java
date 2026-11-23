package week_03;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
public final class CryptoSupport {
 private static final SecureRandom RNG=new SecureRandom();
 private CryptoSupport(){}
 public static byte[] randomBytes(int n){byte[] b=new byte[n];RNG.nextBytes(b);return b;}
 public static String b64(byte[] b){return Base64.getEncoder().encodeToString(b);}
 public static byte[] unb64(String s){return Base64.getDecoder().decode(s.replaceAll("\\s+",""));}
 public static byte[] utf8(String s){return s.getBytes(StandardCharsets.UTF_8);}
 public static String text(byte[] b){return new String(b,StandardCharsets.UTF_8);}
 public static byte[] concat(byte[]... items){int n=0;for(byte[] v:items)n+=v.length;byte[] out=new byte[n];int i=0;for(byte[] v:items){System.arraycopy(v,0,out,i,v.length);i+=v.length;}return out;}
 public static byte[] slice(byte[] b,int start,int end){if(start<0||end<start||end>b.length)throw new IllegalArgumentException("Ban ma qua ngan hoac hong");return Arrays.copyOfRange(b,start,end);}
 public static byte[] gcmEncrypt(byte[] key,byte[] nonce,byte[] data)throws Exception{Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,nonce));return c.doFinal(data);}
 public static byte[] gcmDecrypt(byte[] key,byte[] nonce,byte[] data)throws Exception{Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,nonce));return c.doFinal(data);}
 public static byte[] hkdf(byte[] secret,byte[] salt,byte[] info)throws Exception{Mac m=Mac.getInstance("HmacSHA256");m.init(new SecretKeySpec(salt,"HmacSHA256"));byte[] prk=m.doFinal(secret);m.init(new SecretKeySpec(prk,"HmacSHA256"));m.update(info);m.update((byte)1);return Arrays.copyOf(m.doFinal(),32);}
 public static String toKeyFile(String pub,String pri){return "PUBLIC="+pub.trim()+"\nPRIVATE="+pri.trim()+"\n";}
 public static String[] fromKeyFile(String s){String pub=null,pri="";for(String line:s.split("\\R")){if(line.startsWith("PUBLIC="))pub=line.substring(7).trim();if(line.startsWith("PRIVATE="))pri=line.substring(8).trim();}if(pub==null)throw new IllegalArgumentException("Thieu PUBLIC=");return new String[]{pub,pri};}
}
