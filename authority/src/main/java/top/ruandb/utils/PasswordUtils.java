package top.ruandb.utils;

import java.util.Random;

public class PasswordUtils {
	public final static String[] word = { "a", "b", "c", "d", "e", "f", "g",
			"h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
			"M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public final static String[] num = { "2", "3", "4", "5", "6", "7", "8", "9" };
	
	public static String randomPassword(){
		StringBuffer sb = new StringBuffer();
		Random random = new Random(System.currentTimeMillis());
		boolean flag = false;
		//密码长度为8-10位
		int length = random.nextInt(3) + 8;
		while(length > 0){
			if(flag){
				sb.append(num[random.nextInt(num.length)]);
			}else{
				sb.append(word[random.nextInt(word.length)]);
			}
			flag = !flag ;
			length-- ;
		}
		return sb.toString();
	}
}
