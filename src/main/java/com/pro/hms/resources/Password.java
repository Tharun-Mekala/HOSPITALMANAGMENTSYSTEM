package com.pro.hms.resources;

public class Password {

	public static String generatePassword() {
		String PasswordSet[]= {"ABCDEFGHIJKLMNOPQRSTVUWXYZ","abcdefghijklmnopqrstuvwxyz","!@#$&*?()\\\\/","0123456789"};
		String password="";
		for(int i=0;i<10;i++)
		{
			int idx1=(int)(Math.random()*PasswordSet.length);
			int idx2=(int)(Math.random()*PasswordSet[idx1].length());
			password+=""+PasswordSet[idx1].charAt(idx2);
		}
		return password;
	}

}
