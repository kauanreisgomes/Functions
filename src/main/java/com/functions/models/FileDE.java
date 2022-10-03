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
}
