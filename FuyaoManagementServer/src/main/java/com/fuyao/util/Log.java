package com.fuyao.util;

public class Log {
	private static final boolean on_off = true;
	
	/*
	 * ��ӡ��־
	 **/
	public static void log(String message) {
		if (on_off) {
			System.out.println(message);
		}
	}
}
