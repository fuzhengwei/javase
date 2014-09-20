package com.drdg.endecry;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {

	/**
	 * �ӽ����㷨
	 * 
	 * @param data
	 *            �ӽ�������
	 * @param key
	 *            ��Կ
	 * @param mode
	 *            ģʽ
	 * @return �ӽ��ܽ��
	 */
	public static byte[] desCryt(byte[] data, byte[] key, int mode) {
		byte[] result = null;
		try {
			SecureRandom sr = new SecureRandom();
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			// ����Cipher����
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			// ��ʼ��Cipher����
			cipher.init(mode, secretkey, sr);
			// �ӽ���
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * byte����ת����16�����ַ���
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
	 * 16�����ַ���ת��byte����
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
		// �ӽ���ģʽ
		int mode = Cipher.ENCRYPT_MODE;
		// ���ӽ���byte����16�����ַ���
		String dataHexString = "fdjsalfdfdsauoiu32ro32pu0948u320u";
		// ��Կbyte����16�����ַ���
		String keyHexString = "9AAB1D2EE004AAC3";
		byte[] data = hexString2Bytes(dataHexString);
		byte[] key = hexString2Bytes(keyHexString);
		byte[] result = desCryt(data, key, mode);
		// ��ӡ���
		System.out.println("�����" + bytes2HexString(result));

		// �ӽ���ģʽ
		int mode1 = Cipher.DECRYPT_MODE;
		// ���ӽ���byte����16�����ַ���
		String dataHexString1 = bytes2HexString(result);
		// ��Կbyte����16�����ַ���
		String keyHexString1 = "9AAB1D2EE004AAC3";
		byte[] data1 = hexString2Bytes(dataHexString1);
		byte[] key1 = hexString2Bytes(keyHexString1);
		byte[] result1 = desCryt(data1, key1, mode1);
		// ��ӡ���
		System.out.println("�����" + bytes2HexString(result1));

	}
}
