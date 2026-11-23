package week_03;

import java.util.Scanner;
public final class RSADemo {
 public static void main(String[] args)throws Exception{Scanner in=new Scanner(System.in);System.out.println("=== LAB 3 - RSA OAEP-SHA256 ===");System.out.print("Kich thuoc khoa (2048/3072): ");int bits=Integer.parseInt(in.nextLine());RSACipher rsa=new RSACipher();long t=System.nanoTime();rsa.generateKeys(bits);System.out.printf("Tao khoa trong %.0f ms%n",(System.nanoTime()-t)/1e6);System.out.println("Gioi han ban ro: "+rsa.maxPlaintextBytes()+" byte");System.out.print("Plaintext: ");String plain=in.nextLine();String cipher=rsa.encrypt(plain);System.out.println("Ciphertext ("+cipher.length()+" ky tu Base64): "+cipher);String back=rsa.decrypt(cipher);System.out.println("Giai ma: "+back);System.out.println("Khoi phuc dung: "+plain.equals(back));}
}
