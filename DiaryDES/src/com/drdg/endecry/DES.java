package com.drdg.endecry;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {

	/**
	 * 加解密算法
	 * 
	 * @param data
	 *            加解密数据
	 * @param key
	 *            秘钥
	 * @param mode
	 *            模式
	 * @return 加解密结果
	 */
	public static byte[] desCryt(byte[] data, byte[] key, int mode) {
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			// 创建Cipher对象
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			// 初始化Cipher对象
			cipher.init(mode, secretkey, sr);
			// 加解密
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	/**
	 * 16进制字符串转成byte数组
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] hexString2Bytes(String src) {
		byte[] ret = new byte[8];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static void main(String[] args) {
		// 加解密模式
		int mode = Cipher.ENCRYPT_MODE;
		// 被加解密byte数组16进制字符串
		String dataHexString = "fdjsalfdfdsauoiu32ro32pu0948u320u";
		// 秘钥byte数组16进制字符串
		String keyHexString = "9AAB1D2EE004AAC3";
		byte[] data = hexString2Bytes(dataHexString);
		byte[] key = hexString2Bytes(keyHexString);
		byte[] result = desCryt(data, key, mode);
		// 打印结果
		System.out.println("结果：" + bytes2HexString(result));

		// 加解密模式
		int mode1 = Cipher.DECRYPT_MODE;
		// 被加解密byte数组16进制字符串
		String dataHexString1 = bytes2HexString(result);
		// 秘钥byte数组16进制字符串
		String keyHexString1 = "9AAB1D2EE004AAC3";
		byte[] data1 = hexString2Bytes(dataHexString1);
		byte[] key1 = hexString2Bytes(keyHexString1);
		byte[] result1 = desCryt(data1, key1, mode1);
		// 打印结果
		System.out.println("结果：" + bytes2HexString(result1));

	}
}
