package com.functions.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.functions.models.Combobox;
import com.functions.models.Objeto;

/**
 * @apiNote Classe de conexão com o banco de dados
 * @author kauan reis
 */
public class Query {
    private volatile Connection con;
	private volatile boolean open = false;
	private volatile String url,user,pw;

	/**
	 * @apiNote SQL Server: jdbc:sqlserver://192.168.254.202:1433;databaseName=teste;user=teste;password=123;encrypt=true;trustServerCertificate=true;hostNameInCertificate=cr2.eastus1-a.control.database.windows.net;loginTimeout=30;
	 * @apiNote Oracle: jdbc:oracle:thin:@hostname:port:database
	 * @apiNote MySQL: jdbc:mysql://localhost:3306/sakila
	 * @param url
	 * @param user
	 * @param pw
	 */
	public Query(String url, String user,String pw) {
		this.url = url;
		this.user = user;
		this.pw = pw;
	}

	public void isOpen(boolean isopen){
		open = isopen;
		if(isopen){
			con = ConnectionFactory.getConnection(url,user,pw);
		}else{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void VerifyConnection() throws SQLException{
		if(open){
			if(con == null || con.isClosed()){
				con = ConnectionFactory.getConnection(url,user,pw);
			}
		}
	}

    public List<Object> query(Object[] parametros) {
		List<Object> lista = new ArrayList<>();
		String sql = (String)parametros[0];
		parametros[1] = ((String)parametros[1]).toLowerCase();
		if(parametros.length>2){
			parametros[2] = ((String)parametros[2]).toLowerCase();
		}
		try {
			VerifyConnection();
			try (PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
				
				if (parametros[1].equals("objeto")) {
					Objeto obj = new Objeto();
					ResultSetMetaData meta = rs.getMetaData();

					while (rs.next()) {

						obj = new Objeto();
						for (int i = 1; i <= meta.getColumnCount(); i++) {

							obj.set(meta.getColumnLabel(i), rs.getString(i));						
							
						}

						lista.add(obj);
					}

				} else if (parametros[1].equals("objeto combobox")) {
					Objeto obj = new Objeto();
					ResultSetMetaData meta = rs.getMetaData();

					while (rs.next()) {
						
						obj = new Objeto();

						for (int i = 1; i <= meta.getColumnCount(); i++) {
							
							obj.set(meta.getColumnLabel(i), rs.getString(i));
							
						}

					

						obj.setToString((String)obj.getFirst((String)parametros[2]));
						var values = new ArrayList<String>();

						for (int i = 3; i < parametros.length; i++) {
							values.add((String)parametros[i]);
						}

						obj.setValuesToSearch(values);

						lista.add(obj);
					}

				} else if (parametros[1].equals("relatorio") || parametros[1].equals("objetos")) {
					Objeto obj = new Objeto();
					ResultSetMetaData meta = rs.getMetaData();

					while (rs.next()) {
						
						

						for (int i = 1; i <= meta.getColumnCount(); i++) {
							
							obj.set(meta.getColumnLabel(i), rs.getString(i));
							
						}
						
					}
					
					lista = new ArrayList<>();
					lista.add(obj);
				}

			
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		return lista;

	}

	public List<Objeto> query(String[] parametros) {

		try {
			VerifyConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Objeto> lista = new ArrayList<>();
		String sql = (String)parametros[0];
		parametros[1] = ((String)parametros[1]).toLowerCase();
		if(parametros.length>2){
			parametros[2] = ((String)parametros[2]).toLowerCase();
		}
		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery() ) {
			

			if (parametros[1].equals("objeto")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();

				while (rs.next()) {
				
					obj = new Objeto();

					for (int i = 1; i <= meta.getColumnCount(); i++) {
						
						obj.set(meta.getColumnLabel(i), rs.getString(i));

					}
				
					lista.add(obj);
				}
				
				

            } 
			
			else if (parametros[1].equals("objeto combobox")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();

				while (rs.next()) {

					obj = new Objeto();


					for (int i = 1; i <= meta.getColumnCount(); i++) {

						obj.set(meta.getColumnLabel(i), rs.getString(i));

					}

					obj.setToString((String)obj.getFirst((String)parametros[2]));
					List<String> values = new ArrayList<>();

					for (int i = 3; i < parametros.length; i++) {
						values.add((String)parametros[i]);
					}

					obj.setValuesToSearch(values);

					lista.add(obj);
				}

			}
			
			else if (parametros[1].equals("relatorio") || parametros[1].equals("objetos")) {
                Objeto obj = new Objeto();
                ResultSetMetaData meta = rs.getMetaData();
               
              
				HashMap<String,List<Object>> map = new HashMap<>();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					map.put(meta.getColumnLabel(i), new ArrayList<>());
				}
                while (rs.next()) {
            
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
						var values = map.get(meta.getColumnLabel(i));
						values.add(rs.getString(i));
						map.put(meta.getColumnLabel(i), values);
					}

                }

                lista = new ArrayList<>();
                lista.add(obj);
            }
			
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	
	/***
	 * Recebe
	 * 
	 * @category Sem retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public boolean CED(Object[] parametros) {

		boolean value = false;
		String sql = (String)parametros[0];

		if (sql == null) {
			return value;
		}
		try {
			VerifyConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		try(PreparedStatement ps = con.prepareStatement(sql)){

			value = ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			value = false;
		}
		return value;
	}

/***
	 * Recebe uma consulta e retorna uma lista de valores;
	 * 
	 * @return List<String>
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public List<String> search(Object[] parametros) {

		List<String> dados = new ArrayList<>();
		try {
			VerifyConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		String sql = (String)parametros[0];
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();

			while (rs.next() && rs.getString(1) != null) {
				dados.add(rs.getString(1));
			}
			rs.close();
			if (dados.isEmpty()) {
				dados.add("");
			} /*
				 * else if(Functions.isNull(dados.get(0))){
				 * dados.set(0, "");
				 * }
				 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao executar o comando no banco de dados!\r\nFunção: Query.Search()");
		}

		return dados;
	}

	/***
	 * 
	 * @return String
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public String Count(Object[] parametros) {
		String valor = "0";
		String sql = (String)parametros[0];
		if (sql == null) {
			return valor;
		}

		try {
			VerifyConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getString(1) != null) {
				valor = rs.getString(1);
				try {
					Integer.parseInt(valor);
				} catch (NumberFormatException e) {
					System.out.println("Count só pode receber números\r\n Função: Count");
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao executar o comando no banco de dados!\r\nFunção: Query.Count()");
		}

		return valor;
	}

	// Um dado extra
	/***
	 * Pega uma String sql e pega um id e um nome. Sintaxe:
	 * *SELECT valor1 as id, valor2 as nome FROM tabela*
	 * Possivel também:
	 * *SELECT valor1 as id, valor2 as nome, valor3 as dado FROM tabela*
	 * 
	 * @return List<Combobox>
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public List<Combobox> listCb(Object[] parametros) {
		List<Combobox> l = new ArrayList<>();
		String sql = (String)parametros[0];
		if (!(sql == null || sql.isEmpty())) {

			try {
				VerifyConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}

			try(PreparedStatement ps = con.prepareStatement(sql)){
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Combobox i = new Combobox(rs.getInt("id"), rs.getString("nome"));
					ResultSetMetaData rsmd = rs.getMetaData();

					if (rsmd.getColumnCount() > 2) {
						i.setDadoextra(rs.getString("dado"));
					}
					l.add(i);

				}
				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro ao executar o comando no banco de dados!\r\n Função: Query.listCb()");
			}
		}

		return l;

	}

}
