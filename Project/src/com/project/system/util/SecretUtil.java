package com.project.system.util;

import org.apache.log4j.Logger;

import Decoder.BASE64Encoder;

import com.alibaba.druid.filter.config.ConfigTools;
import com.capinfo.crypt.MD5;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;


/**
 * 加密类
 * 
 * @author Administrator
 * 
 */
public class SecretUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecretUtil.class);

	/**
	 * 
	 * 对字符串进行SHA－１加密,SHA-256及SHA-512生成密码太长，不支持
	 * 
	 * @param password
	 *            要加密的密码信息
	 * @return　密码进行MD5加密后的密码
	 * 
	 * @author 张青 2013-9-26
	 */
	public static String encryptToSHA(String password) {
		byte[] digesta = null;
		try {
			// 得到一个MD5的消息摘要
			MessageDigest mdi = MessageDigest.getInstance("SHA-1");
			// 添加要进行计算摘要的信息
			mdi.update(password.getBytes());
			// 得到该摘要
			digesta = mdi.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此加密方式：", e);
		}

		return byteToHex(digesta);
	}

	/**
	 * 十六进制字符串转化二进制
	 * 
	 * @param hex
	 *            十六进制字符串
	 * @return
	 */
	public static byte[] hexToByte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();

		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 
	 * 将二进制转化为16进制字符串
	 * 
	 * @param pwd
	 *            二进制字节数组
	 * @return String 转化16进制后的字符串
	 * 
	 * @author 张青 2013-9-26
	 */
	public static String byteToHex(byte[] pwd) {
		StringBuilder hs = new StringBuilder("");
		String temp = "";
		for (int i = 0; i < pwd.length; i++) {
			temp = Integer.toHexString(pwd[i] & 0XFF);
			if (temp.length() == 1) {
				hs.append("0").append(temp);
			} else {
				hs.append(temp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	// 十六进制下数字到字符的映射数组
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/** 对字符串进行MD5加密 */
	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static String encodeByHMAC(String originString, String key) {
		MD5 md5 = new MD5("");
		try {
			md5.hmac_MD5(originString, key);
			byte b[] = md5.getDigest();
			String digestString = MD5.stringify(b);
			return digestString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param 字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** 将一个字节转化成十六进制形式的字符串 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args) {

		String pwd1 = "rjxy";
		String pwd2 = "374FEC05192452A25950535192BD12A668017F33";

		System.out.println("未加密的密码:" + pwd1);
		// 将123加密
		pwd2 = SecretUtil.encryptToSHA(pwd1);
		System.out.println("加密后的密码:" + pwd2);

		System.out.print("验证密码是否下确:");
		try {
			System.out.println(ConfigTools.decrypt("Iweux3zW3lrD7aVjub6Pj+/y+JheNSPy2AhG+Mc1Pn8X/BLWsUARldB9P5vzqFLlxRaK626gzBWmOxZpy/bnyQ=="));
			System.out.println(ConfigTools.decrypt("bpRuH5fOWs2F7wmMpgIdgESiSI0SD7+zIJMYkuizL2EpYMjooP1I9P+4xTDQ0NEbiYwPeGP9LD6S1ZUZ1uf4bw=="));
			System.out.println(ConfigTools.encrypt("cykj_mysql199"));
			System.out.println(ConfigTools.encrypt("cyadmin199cn"));
			System.out.println(ConfigTools.encrypt("cy199cn1816"));
			System.out.println(ConfigTools.encrypt("mySQL2016*()"));
			System.out.println(ConfigTools.encrypt("1234qwer"));
			System.out.println(ConfigTools.encrypt("root"));
			
			

	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(SecretUtil.encryptToSHA("13476607441"));

	}

}
