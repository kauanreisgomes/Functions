package com.functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @apiNote Classe de Funções gerais
 * @author kauan reis
 */
public class Functions {
	public static int index;
	private volatile static HttpURLConnection connection;

	
	public static boolean isNull(Object value) {
		String texto = "";
		if(value == null){
			return true;
		}else if(value.getClass() == String.class){
			texto = (String)value;
			
		}else{
			System.out.println("Erro ao identificar o tipo de dado!\r\nFunção: Functions.isNull()");
			return false;
		}

		if (texto == null || texto.isEmpty() || texto.equals("") || texto.isBlank()) {
			return true;
		}

		return false;
	}

	public static boolean isNull(Object[] p) {
		for (int i = 0; i < p.length; i++) {
			Object value = p[i];
			String texto;
			if(value == null){
				return true;
			}else if(value.getClass() == String.class){
				texto = (String)value;
				
			}else{
				System.out.println("Erro ao identificar o tipo de dado!\r\nFunção: Functions.isNull()");
				return true;
			}

			if (texto == null || texto.isEmpty() || texto.equals("") || texto.isBlank()) {
				return true;
			}

			
		}
		return false; 
		
	}

	/***
	 * A função parseDate serve para passar uma string para o tipo calendar ou
	 * passar um calendar para o tipo String.
	 * 
	 * @param parametros
	 * @see {String/Calendar, FormatDate(String), parse/format(String)}
	 * @return Object
	 */
	public static Object parseDate(Object[] parametros) {
		var s = new SimpleDateFormat((String) parametros[1]);
		var date = Calendar.getInstance();
		var res = (String) parametros[2];

		if (res.equals("parse")) {
			
			try {
				date.setTime(s.parse((String) parametros[0]));
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro ao passar a data!\r\nFunção: Functions.ParserDate()");
				return null;
			}
		} 
		
		else if (res.equals("format")) {
			date = (Calendar) parametros[0];
			var rtn = s.format(date.getTime());
			return rtn;
		}
		
		else if(res.equals("format string")){
			try {
				date.setTime(s.parse((String) parametros[0]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s = new SimpleDateFormat((String) parametros[3], new Locale ("pt", "BR"));
			var rtn = s.format(date.getTime());
			return rtn;
		}
		
		else {
			return null;
		}
	}
/***
	 * A função verify serve para verificar um objeto, para ver se ele está com os
	 * dados que queremos na hora de um cadastrou ou atualização.
	 * 
	 * @param parametros
	 * @see {Formato(String), tipo do objeto(String), objeto, ...}
	 * @return Object
	 */
	public static boolean verify(Object[] lista) {
		var res = ((String) lista[0]).toLowerCase();
		/*var tipo = "";
		if(lista[1].getClass().equals(String.class)){
			tipo = ((String) lista[1]).toLowerCase();
		}*/
		

		if (res.equals("string")) {

			
			for (int i = 1; i < lista.length; i++) {
				var var = (String) lista[i];
				if (isNull(var)) {
					return true;
				}
			}
			

		} else if (res.equals("int") || res.equals("double") || res.equals("long")) {
			for (int i = 1; i < lista.length; i++) {
				var var = (String) lista[i];
				
				if (isNull(var) || (filtro(var) && res.equals("int"))) {
					
					return true;
				}else if (res.equals("double")){
					try{
						Double.parseDouble(var);
					}catch(NumberFormatException e){
						
						return true;
					}
				}else if (res.equals("long")){
					try{
						Long.parseLong(var);
					}catch(NumberFormatException e){
						
						return true;
					}
				}
			}
			

		} else if(res.equals("cpf/cnpj")){
			for (int i = 1; i < lista.length; i++) {
				String txtcnpjecpf = (String)lista[i];
				if((txtcnpjecpf.length() > 14 && (txtcnpjecpf.contains(".") || txtcnpjecpf.contains("-") || txtcnpjecpf.contains("/"))) || (txtcnpjecpf.length() > 12 && 
				!(txtcnpjecpf.contains(".") || txtcnpjecpf.contains("-") || txtcnpjecpf.contains("/")))){
					if(!(isCNPJ(txtcnpjecpf.replace("-", "").replace(".", "").replace("/", "")))){
					
						
						return true;
					}
					if(!(isCNPJ(txtcnpjecpf.replace("-", "").replace(".", "").replace("/", "")))){
						
						return true;
					}
				}else{
					if(!(isCPF(txtcnpjecpf.replace(".", "").replace("-", "")))){
						
						return true;
					}
					if(!(isCPF(txtcnpjecpf.replace(".", "").replace("-", "")))){
						
						return true;
					}
				}
			}
		}
		else if(res.equals("cpf")){
			for (int i = 1; i < lista.length; i++) {
				String txtcpf = (String)lista[i];
				if(!(isCPF(txtcpf.replace(".", "").replace("-", "")))){
					
					return true;
				}
				if(!(isCPF(txtcpf.replace(".", "").replace("-", "")))){
					
					return true;
				}
			}
		}else if(res.equals("cnpj")){
			for (int i = 2; i < lista.length; i++) {
				String txtcnpj = (String)lista[i];
				if(!(isCNPJ(txtcnpj.replace("-", "").replace(".", "").replace("/", "")))){
					
					return true;
				}
				if(!(isCNPJ(txtcnpj.replace("-", "").replace(".", "").replace("/", "")))){
					
					return true;
				}
			}
		}
		return false;
	}

	public static List<Object> format(Object[] p){
		int tipo = (int)p[0];
		List<Object> formated = new ArrayList<>();
		switch(tipo){
			//formata para o tipo numero
				case 1:
						NumberFormat format = NumberFormat.getNumberInstance();
						for (int i = 1; i < p.length; i++) {
							double d = 0.0;
							try{
								d = Double.parseDouble((String)p[i]);
							}catch(NullPointerException | NumberFormatException e){
								d = 0.0;
							}
							formated.add(format.format(d));
						}
						
					break;
					//formata para tipo moeda
				case 2:
					NumberFormat formatc = NumberFormat.getCurrencyInstance();
						for (int i = 1; i < p.length; i++) {
							double d = 0.0;
							try{
								d = Double.parseDouble((String)p[i]);
							}catch(NullPointerException | NumberFormatException e){
								d = 0.0;
							}
							formated.add(formatc.format(d));
						}
					break;
				case 3:
						for (int i = 3; i < p.length; i++) {
							String number = "0";
							try{
								if(((String)p[2]).equals("double")){
									number = String.format((String)p[1], Double.parseDouble((String)p[i]));
								}else if(((String)p[2]).equals("isdouble")){
									number = String.format((String)p[1], p[i]);
								}
								
							}catch(NullPointerException | NumberFormatException e){
								number = "0";
							}catch(IllegalFormatException e1){
								System.out.println(e1);
								number = "0";
							}
							formated.add(number);
						}
					break;

		}
		
		return formated;
	}

	public static List<Object> parse(Object[] p){
		List<Object> l = new ArrayList<>();
		if(p[1] == null){
				l.add(0);
				return l;
		}
		switch((int)p[0]){
				case 1:
						for (int i = 1; i < p.length; i++) {
								if(p[i] == null){
										l.add(0.0);
								}else{
										l.add(Double.parseDouble((String)p[i]));
								}

						}
				break;
				case 2:
				for (int i = 1; i < p.length; i++) {
					if(p[i] == null){
							l.add(0);
					}else{
							l.add(Integer.parseInt((String)p[i]));
					}

			}
				break;
		}
		return l;
}
	
	/**
	 * @apiNote Executa um comando no cmd do computador.
	 * @param command 
	 */
	public static void ExecuteCommandLine(String command){
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @apiNote Adiciona valores em um arquivo JSON (normalmente), podendo ser alterado para outros tipos de arquivos.
	 * @apiNote 0 - option
	 * @apiNote 1 - File PATH
	 * @apiNote 2 - option 1 - JSONObject || option 2 - String
	 * @throws IOException
	 * @param parametros
	 */
	public static void addvalues(Object[] parametros){
		int option = (int) parametros[0];

		if(option == 1){
			JSONObject cache = (JSONObject)parametros[2];
	
			File f = new File((String)parametros[1]);
			if(f.exists()){
				try (FileWriter fw = new FileWriter(f)) {
					BufferedWriter bw = new BufferedWriter( fw );
					bw.write(cache.toString());
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else{
				try {
					f.createNewFile();
					addvalues(parametros);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else{
			String log = (String)parametros[2];
		
			File f = new File((String)parametros[1]);
			if(f.exists()){
				try (FileWriter fw = new FileWriter(f)) {
					BufferedWriter bw = new BufferedWriter( fw );
					bw.write(log);
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else{
				try {
					f.createNewFile();
					addvalues(parametros);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

	/**
	 * @apiNote 0 - option (int)
	 * @apiNote 1 - local\nomdoarquivo.txt (String)
	 * @throws NullPointerException
	 * @throws JSONException
	 * @throws IOException
	 * @param parametros
	 * @return
	 */ 
	public static List<Object> getvalues(Object[] parametros){
		int option = (int) parametros[0];
		List<Object> lista = new ArrayList<>();
		if(option == 1){
			JSONObject cache = new JSONObject();
			
			File f = new File((String)parametros[1]);
			if(f.exists()){
				try (FileReader fw = new FileReader(f)) {
					BufferedReader bw = new BufferedReader( fw );
					
					try{
						String i = "";
						var l = bw.lines().toList();
						for (int index = 0; index < l.size(); index++) {
							i = i + l.get(index);	
						}
					
						cache = new JSONObject(i);
						lista.add(true);
						lista.add(cache);
					}catch(NullPointerException | JSONException e){
						lista.add(false);
						return lista;
					}
						
					
					
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					lista.add(false);
				}

			}else{
				try {
					if(f.createNewFile() == true){
						lista = getvalues(parametros);
					}else{
						lista.add(false);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					lista.add(false);
				}
			}	
		}
		
		return lista;
	}

	/**
	 * @apiNote Ler arquivos da pasta json, sendo necessário somento o File. 
	 * @param arquivo
	 * @return
	 * @throws FileNotFoundException
	 * @throws JSONException
	 */
	public static JSONObject JsonReader(File arquivo){
		if(arquivo.exists()){
			try (Scanner scanner = new Scanner(new FileInputStream(arquivo)).useDelimiter("\\n")) {
				
				String json = "";
				while(scanner.hasNext()){
					json += scanner.next();
				}
				JSONObject jsonobj = new JSONObject(json);
				return jsonobj;
			} catch (JSONException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else{
            
        }
		
		return null;
	
	}


	public static List<Object> getJSONfromweb(Object[] parametros) {
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		List<Object> l = new ArrayList<>();
		// Codificando login e senha para o formato url
		/*login = URLEncoder.encode(login, StandardCharsets.UTF_8);
		password = URLEncoder.encode(password, StandardCharsets.UTF_8);*/

		try {
			// Estabelecendo conexão
			URL url = new URL((String)parametros[0]);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			if (status == 500) {
				System.out.println("Erro ao acessar o serviço web!\r\nFunção: Functions.getJSONfromweb()");
				l.add(false);
			}
			if (status == 200) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// Passando o corpo JSON
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				// Verificando o JSON
			String responsebody = responseContent.toString();
				if (responsebody.isEmpty() || responsebody == null) {
					System.out.println("Sem corpo JSON!\r\n Função: Functions.getJSONfromweb()");
					l.add(false);
					
				}
				reader.close();
				l.add(true);
				if((int)parametros[1] == 1){
					var jsonarray = new JSONObject(responsebody);
					l.add(jsonarray);
				}else{
					var jsonarray = new JSONArray(responsebody);
					l.add(jsonarray);
				}
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}

		return l;
	}
	


	/**
	 * @apiNote Cria um arquivo temporario no temp/GPE. Recebendo o local do arquivo que vai ser movido e o nome
	 * @param location
	 * @param fileName
	 * @return
	 */
	public static Object createFileTemp(File file,String fileName){
		
        //caminho da imagem dentro da pasta tmp do sistema
        String destino = System.getenv("APPDATA")+"/" + fileName;

		System.out.println(System.getenv("APPDATA"));
        try (InputStream inputImagem = new FileInputStream(file)) {
			try (OutputStream outputImagem = new FileOutputStream(destino)) {
				byte[] buffer = new byte[inputImagem.available()];
				try {
					inputImagem.read(buffer);
					outputImagem.write(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				if(inputImagem != null){
				    inputImagem.close();
				}
				if(outputImagem!=null){
				    outputImagem.close();
				}
				return destino;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * @apiNote Verifica se o CNPJ é válido, recebendo uma string com o CNPJ como parametro
	 * @param CNPJ
	 * @return
	 */
	public static boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
			if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
				CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
				CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
				CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
				CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
				(CNPJ.length() != 14))
				return(false);
		
			char dig13, dig14;
			int sm, i, r, num, peso;
		
		// "try" - protege o código para eventuais erros de conversao de tipo (int)
			try {
		// Calculo do 1o. Digito Verificador
				sm = 0;
				peso = 2;
				for (i=11; i>=0; i--) {
		// converte o i-ésimo caractere do CNPJ em um número:
		// por exemplo, transforma o caractere '0' no inteiro 0
		// (48 eh a posição de '0' na tabela ASCII)
				num = (int)(CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
				}
		
				r = sm % 11;
				if ((r == 0) || (r == 1))
					dig13 = '0';
				else dig13 = (char)((11-r) + 48);
		
		// Calculo do 2o. Digito Verificador
				sm = 0;
				peso = 2;
				for (i=12; i>=0; i--) {
				num = (int)(CNPJ.charAt(i)- 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
				}
		
				r = sm % 11;
				if ((r == 0) || (r == 1))
					dig14 = '0';
				else dig14 = (char)((11-r) + 48);
		
		// Verifica se os dígitos calculados conferem com os dígitos informados.
				if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
					return(true);
				else return(false);
			} catch (InputMismatchException erro) {
				return(false);
			}
	}
	/***
	 * @apiNote Verifica se o CPF é válido, recebendo uma string com o CPF como parametro
	 * @param CNPJ
	 * @return
	 */
	public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

	/**
	 * @apiNote Filtro para verificar se é inteiro
	 * @apiNote Use a função verify ao inves desta!
	 * @deprecated
	 * @param valor
	 * @return
	 */
	@Deprecated
	public static boolean filtro(String valor) {
		if (Functions.isNull(valor)) {
			return true;
		}

		try {
			Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			return true;
		}

		// Se não tiver letras ou simbolos alem de numeros ele retorna false
		if (!(valor.matches("[+-]?\\d*(\\.\\d+)?")) || valor.contains(".") || valor.contains(",")) {
			return true;
		}

		return false;
	}

	public static String clearFormating(String phoneNumber) {
		phoneNumber = phoneNumber.replace("(", "").replace(")", "").replace("-", "")
				.replace(" ", "").replace(".", "");
		return phoneNumber;
	}
	
	public static String formatPhoneNumber(String phoneNumber) {
		StringBuilder sb = new StringBuilder();
		if (phoneNumber.length() >= 5 && phoneNumber.length() < 9) {
			sb.append(phoneNumber.subSequence(0, 4));
			sb.append('-');
			sb.append(phoneNumber.subSequence(4, phoneNumber.length()));
		} else if (phoneNumber.length() == 9) {
	
			sb.append(phoneNumber.subSequence(0, 5));
			sb.append('-');
			sb.append(phoneNumber.subSequence(5, phoneNumber.length()));
	
		} else if (phoneNumber.length() == 10) {
	
			sb.append("(");
			sb.append(phoneNumber.subSequence(0, 2));
			sb.append(") ");
			sb.append(phoneNumber.subSequence(2, 6));
			sb.append("-");
			sb.append(phoneNumber.subSequence(6, phoneNumber.length()));
	
		} else if (phoneNumber.length() == 11) {
			if (phoneNumber.startsWith("0")) {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 3));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(3, 7));
				sb.append("-");
				sb.append(phoneNumber.subSequence(7, phoneNumber.length()));
	
			} else {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 2));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(2, 7));
				sb.append("-");
				sb.append(phoneNumber.subSequence(7, phoneNumber.length()));
			}
	
		} else if (phoneNumber.length() == 12) {
			if (phoneNumber.startsWith("0")) {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 3));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(3, 8));
				sb.append("-");
				sb.append(phoneNumber.subSequence(8, phoneNumber.length()));
	
			} else {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 2));
				sb.append(" ");
				sb.append(phoneNumber.subSequence(2, 4));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(4, 8));
				sb.append("-");
				sb.append(phoneNumber.subSequence(8, phoneNumber.length()));
			}
	
		} else if (phoneNumber.length() == 13) {
			if (phoneNumber.startsWith("0")) {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 3));
				sb.append(" ");
				sb.append(phoneNumber.subSequence(3, 5));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(5, 9));
				sb.append("-");
				sb.append(phoneNumber.subSequence(9, phoneNumber.length()));
			} else {
				sb.append("(");
				sb.append(phoneNumber.subSequence(0, 2));
				sb.append(" ");
				sb.append(phoneNumber.subSequence(2, 4));
				sb.append(") ");
				sb.append(phoneNumber.subSequence(4, 9));
				sb.append("-");
				sb.append(phoneNumber.subSequence(9, phoneNumber.length()));
			}
	
		} else if (phoneNumber.length() == 14) {
			sb.append("(");
			sb.append(phoneNumber.subSequence(0, 3));
			sb.append(" ");
			sb.append(phoneNumber.subSequence(3, 5));
			sb.append(") ");
			sb.append(phoneNumber.subSequence(5, 10));
			sb.append("-");
			sb.append(phoneNumber.subSequence(10, phoneNumber.length()));
	
		} else {
			sb.append(phoneNumber);
		}
		return sb.toString();
	}
}
