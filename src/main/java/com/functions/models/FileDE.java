package com.functions.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @apiNote Classe para encripitação e Decriptação de arquivos
 * @author kauan reis
 */
public class FileDE {
  

	public static byte[] encode(String password, InputStream is) throws IOException {

		byte[] bytes = is.readAllBytes();
		BasicTextEncryptor e = new BasicTextEncryptor();
		e.setPassword(password);

		return e.encrypt(new String(bytes)).getBytes();
	}

	public static byte[] dencode(String password, InputStream is) throws IOException {
		byte[] b = is.readAllBytes();
		BasicTextEncryptor e = new BasicTextEncryptor();
		e.setPassword(password);
		
		String h = e.decrypt(new String(b));
		return h.getBytes();
	
	}

	public static byte[] encode(Object[] parametros){
		int type = (int)parametros[0];
		String pw = new String(Base64.getDecoder().decode(parametros[1].toString()));
		String rtn = "";
		BasicTextEncryptor bte = new BasicTextEncryptor();
		bte.setPassword(pw);
		switch(type){
			case 1 : 
				String value = (String)parametros[2];
				rtn = bte.encrypt(value);
				break;
			case 2 : 
				value = new String((byte[])parametros[2]);
				rtn = bte.encrypt(value);
				break;
		}

		return rtn.getBytes();
	}

	public static byte[] dencode(Object[] parametros){
		int type = (int)parametros[0];
		String pw = new String(Base64.getDecoder().decode(parametros[1].toString()));
		String rtn = "";
		BasicTextEncryptor bte = new BasicTextEncryptor();
		bte.setPassword(pw);
		switch(type){
			case 1 : 
				String value = (String)parametros[2];
				rtn = bte.decrypt(value);
				break;
			case 2 : 
				value = new String((byte[])parametros[2]);
				rtn = bte.decrypt(value);
				break;
		}

		return rtn.getBytes();
	}
}
