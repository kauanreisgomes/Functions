package com.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import com.functions.models.Combobox;
import com.functions.models.Objeto;
import com.functions.models.PopUp;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @apiNote Classe de Funções gerais com funções FX
 * @author kauan reis
 */
public class FunctionsFX {
	public static int index;

	/**
	 * 
	 * <Ordem do vetor>
	 * *Posição 0 - Variavel para pegar do Objeto
	 * *Posição 1 - Nome do Objeto
	 * Posição 2 - Style
	 * Posição 3 - Variavel do Objeto para condicional
	 * Posição 4 - String para Condição
	 * 
	 * @apiNote Posições com (*) não podem ser nulas! | Recebe um vetor(parametros)
	 *          e uma coluna
	 **/
	public static void definevalues(String[] parametros, TableColumn<Object, String> t) {
		if (parametros == null || t == null) {
			Object[] msg = {"Atenção","Parametros não podem ser nulos!", 1};
			dialogBox(msg);
			return;
		}
		

		Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory = new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
			public TableCell<Object, String> call(final TableColumn<Object, String> param) {
				final TableCell<Object, String> cell = new TableCell<Object, String>() {

					public void updateItem(String item, boolean empty) {

						super.updateItem(item, empty);

						if (empty || item == null) {

							setGraphic(null);

						}

						else {
								
								Objeto obj = (Objeto)getTableView().getItems().get(getIndex());
								if (parametros.length > 3) {
									if(obj.getFirst(parametros[0]) != null){
										
										if (((String)obj.getFirst(parametros[3])).toLowerCase().equals(parametros[4])) {
											
											if(!(parametros.length >= 6)){
												this.setStyle(parametros[2]);
											}else{
												getTableRow().setStyle(parametros[2]);
											}

										} else {
											if(!(parametros.length >= 6)){
												this.setStyle("");
											}else{
												getTableRow().setStyle("");
											}
										}
									}
								} else if (parametros.length > 2) {

									//getTableColumn().setStyle();
									this.setStyle(parametros[2]);

								}else{
									this.setStyle("");
								}							
								if(!isNull((String)obj.getFirst(parametros[0])) || item != null){
									setText((String)obj.getFirst(parametros[0]));
								}
							setGraphic(null);

						}
					}
				};
				return cell;
			}
		};
		t.setCellFactory(cellFactory);
		if(parametros[1].toLowerCase().equals("objeto")){
			t.setCellValueFactory(celldata -> new SimpleStringProperty(""+((Objeto)celldata.getValue()).getFirst(parametros[0])));
		}
		
	}

	
	public static void definevalues(String[] parametros, TableColumn<Object, String>[] tabelas,String[] style) {
		if (parametros == null || tabelas == null) {
			Object[] msg = {"Atenção","Parametros não podem ser nulos!", 1};
			dialogBox(msg);
		}else if(parametros.length != tabelas.length){
			Object[] msg = {"Atenção","Erro ao definir os valores, parametros e tabelas tem valores diferentes!", 1};
			dialogBox(msg);
		}else{
			
			for (int i = 0; i < parametros.length; i++) {
				String name = parametros[i];
				Callback<TableColumn<Object, String>, TableCell<Object, String>> cellFactory = new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
					public TableCell<Object, String> call(final TableColumn<Object, String> param) {
						final TableCell<Object, String> cell = new TableCell<Object, String>() {
		
							public void updateItem(String item, boolean empty) {
		
								super.updateItem(item, empty);
		
								if (empty || item == null) {
		
									setGraphic(null);
		
								}
		
								else {
									
									Objeto obj = (Objeto)getTableView().getItems().get(getIndex());
									if(obj.getFirst(name) != null){
										if (style != null && style.length > 1) {
											
											if (((String)obj.getFirst(style[1])).toLowerCase().equals(style[2].toLowerCase())) {		
												
												this.setStyle(style[0]);
												
											} else {
											
												this.setStyle("");
												
											}
											
										} else if (style != null && style.length == 1) {
			
											//getTableColumn().setStyle();
											this.setStyle(style[0]);
			
										}else{
											this.setStyle("");
										}	
									}						

									if(!isNull((String)obj.getFirst(name)) || item != null){
										setText((String)obj.getFirst(name));
									}
									
								
									setGraphic(null);
		
								}
							}
						};
						return cell;
					}
				};
				
				tabelas[i].setCellFactory(cellFactory);
				tabelas[i].setCellValueFactory(celldata -> new SimpleStringProperty(""+((Objeto)celldata.getValue()).getFirst(parametros[0])));
			}
			
			
		}
		

		
	}

	/**
	 * *Posição 0 - Texto do botão |
	 * *Posição 1 - Style |
	 * Posição 2 - Endereço da imagem
	 * 
	 * @apiNote Recebe TableColumn, String[](vetor) e um EventHandler(Podendo ser
	 *          nulo)
	 * @apiNote Posições com (*) não podem ser nulas! | Use Functions.index, para
	 *          pegar o index da linha que o botão se encontra.
	 **/
	@Deprecated
	public static void addbuttons(TableColumn<Object, Void> t, String[] parametros, EventHandler<ActionEvent> evento) {
		Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {

			public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
				final TableCell<Object, Void> cell = new TableCell<Object, Void>() {
					private final Button btn = new Button();
					{
						btn.setStyle(parametros[1]);
						if (parametros.length > 2) {
							File f = new File(parametros[2]);
							if(f.exists()){
								btn.setGraphic(tratamentodeIcon(f));
							}else{
								System.out.println("Arquivo "+parametros[2]+" não existe!\r\nFunção: FunctionsFX.addbuttons().");
							}
						}
						btn.setText(parametros[0]);

						// ação
						btn.focusedProperty().addListener((obs, wasFocused, isfocused) -> {
							if (isfocused) {
								index = getIndex();
							}
						});
						btn.setOnAction(evento);

					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
						} else {

							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		t.setCellFactory(cellFactory);
	}

	public static void addbuttons2(TableColumn<Object, Void> t, String[] parametros, EventHandler<ActionEvent> evento) {
		Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<TableColumn<Object, Void>, TableCell<Object, Void>>() {

			public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
				final TableCell<Object, Void> cell = new TableCell<Object, Void>() {
					private final Button btn = new Button();
					{
						btn.setStyle(parametros[1]);
						if (parametros.length > 2) {
							File f = new File(parametros[2]);
							if(f.exists()){
								btn.setGraphic(tratamentodeIcon(f));
							}else{
								System.out.println("Arquivo "+parametros[2]+" não existe!\r\nFunção: FunctionsFX.addbuttons2().");
							}
						}
						btn.setText(parametros[0]);

						// ação
						btn.focusedProperty().addListener((obs, wasFocused, isfocused) -> {
							if (isfocused) {
								getTableView().getSelectionModel().select(getIndex());
							}
						});
						btn.setOnAction(evento);

					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
						} else {

							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		t.setCellFactory(cellFactory);
	}

	/**
	 * Função criada para pegar uma imagem e transformar em ImageView para colocar
	 * como icone em botões
	 * 
	 * @apiNote *Posição 0 - local | Não pode ser nulo
	 **/
	public static ImageView tratamentodeIcon(File f) {
		
		Image image;
		try {
			image = new Image(new FileInputStream(f));
			return new ImageView(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao passar o arquivo para imageView\r\nFunção: FunctionsFX.tratamentodeIcon()");
		}
	
		return null;
	}

	/**
	 * @apiNote Verifica se o Objeto JavaFX é nulo.
	 * @apiNote Objetos válidos: TextField, TextArea, ComboBox, DatePicker, String.
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		String texto = "";
		if(value == null){
			return true;
		}else if(value.getClass() == String.class){
			texto = (String)value;
			
		}else if(value.getClass() == TextField.class){
			TextField tf = (TextField)value;
			texto = tf.getText();
		}else if(value.getClass() == TextArea.class){
			TextArea ta = (TextArea)value;
			texto = ta.getText();
		}else if(value.getClass() == ComboBox.class){
			ComboBox c = (ComboBox)value;
			if(c.getValue() != null){
				return false;
			}else{
				return true;
			}
		}else if(value.getClass() == DatePicker.class){
			DatePicker dt = (DatePicker)value;
			if(dt.getValue() != null){
				return false;
			}else{
				return true;
			}
		}else{
			Object[] msg = {"Atenção","Erro ao indentificar o tipo do objeto!", 1};
			dialogBox(msg);
			System.out.println("Erro ao indentificar o tipo do objeto!\r\nFunção: FunctionsFX.isNull()");
			return false;
		}

		if (texto == null || texto.isEmpty() || texto.equals("") || texto.isBlank()) {
			return true;
		}

		return false;
	}

	/**
	 * @apiNote Verifica se os Objetos JavaFX são nulos.
	 * @apiNote Objetos válidos: TextField, TextArea, ComboBox, DatePicker, String.
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object[] p) {
		for (int i = 0; i < p.length; i++) {
			Object value = p[i];
			String texto;
			if(value == null){
				return true;
			}else if(value.getClass() == String.class){
				texto = (String)value;
				
			}else if(value.getClass() == TextField.class){
				TextField tf = (TextField)value;
				texto = tf.getText();
			}else if(value.getClass() == ComboBox.class){
				ComboBox c = (ComboBox)value;
				if(c.getValue() != null){
					continue;
				}else{
					return true;
				}
			}else{
				Object[] msg = {"Atenção","Erro ao indentificar o tipo do objeto! "+ value.getClass(),1};
				dialogBox(msg);
				System.out.println("Erro ao indentificar o tipo do objeto!\r\nFunção: FunctionsFX.isNull(); Classe do Objeto:"+ value.getClass());
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
				Object[] p = {"Atenção","Erro ao passar a data!\r\nFunção: FunctionsFX.parseDate()",1};
				dialogBox(p);
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

	/**
	 * @apiNote Formata o Objeto DatePicker do JavaFX. Sendo somente necessário passar o DatePicker como parametro
	 * @param date
	 */
	public static void formatDatePicker(DatePicker date){
		date.getEditor().setPromptText("dd/MM/yyyy");
		date.setOnKeyReleased(e->{
			if(!(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.UP)){
				releasedDate(date);
			}
		});
		date.focusedProperty().addListener((obs,wasfocused,isfocused)->{
			if(!isfocused){
				try{
					date.setValue(date.getConverter().fromString(date.getEditor().getText()));
				}catch(DateTimeParseException | NullPointerException e){
					date.getEditor().setText(null);
					date.setValue(null);
				}
			}
		});
		dateFormat(date);
	}
	/**
	 * @apiNote Formata Objetos do tipo DatePicker do JavaFX. Sendo somente necessário passar o array DatePicker como parametro
	 * @param date
	 */
	public static void formatDatePicker(DatePicker[] dates){
		for (int index = 0; index < dates.length; index++) {
			DatePicker date = dates[index];
			date.getEditor().setPromptText("dd/MM/yyyy");
			date.setOnKeyReleased(e->{
				if(!(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.UP)){
					releasedDate(date);
				}
			});
			date.focusedProperty().addListener((obs,wasfocused,isfocused)->{
				if(!isfocused){
					try{
						date.setValue(date.getConverter().fromString(date.getEditor().getText()));
					}catch(DateTimeParseException | NullPointerException e){
						date.getEditor().setText(null);
						date.setValue(null);
					}
				}
			});
			dateFormat(date);
		}
	}

	/***
	 * @apiNote 1
	 * @param parametros
	 */
	public static void formatRelased(Object[] parametros){
		int formato = (int) parametros[0];
		int tipo = (int) parametros[1];
		if(formato==1){
			if(tipo == 1){
				//Formatação de números inteiros
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = ((TextField)parametros[i]);
					tf.setOnKeyReleased(e->{
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
						|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
							return;
						}
						try{
							Long.parseLong(tf.getText());
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
						}
					});
				}
			} else if(tipo == 2){
				//Formatação de valores double
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = ((TextField)parametros[i]);
					tf.setOnKeyReleased(e->{
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						try{
							
							Double.parseDouble(tf.getText().replace(",", "."));
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
						}
					});
					tf.focusedProperty().addListener((obs,wasfocused,isfocused)->{
						if(!isfocused){
							if(!Functions.isNull(tf.getText())){
								tf.setText(tf.getText().replace(",", "."));
							}
							
						}
					});
				}
			} else if(tipo == 3){
				//Formatação de cpf
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = (TextField)parametros[i];
					tf.setOnKeyReleased(e->{ 
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						try{
							Long.parseLong(tf.getText().replace("-", "").replace(".", ""));
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
							return;
						}
						
						
				
						if(tf.getText().length() == 3 || tf.getText().length() == 7){
							tf.setText(tf.getText() + ".");
						}else if(tf.getText().length() == 11){
							tf.setText(tf.getText() + "-");
						}

						if(tf.getText().length() > 14){
							tf.setText(tf.getText().substring(0,14));
						}

						tf.positionCaret(tf.getText().length());
					});

				}
			}else if(tipo == 4){
				for (int i = 2; i < parametros.length; i++) {

					TextField tf = ((TextField)parametros[i]);
					tf.setOnKeyReleased(e->{
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						try{
							Long.parseLong(tf.getText());
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
						}
					});
					
				}
			}else if(tipo == 5){
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = (TextField)parametros[i];
					
					tf.setOnKeyReleased(e->{ 
						int type = 1;
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						if(Functions.isNull(tf.getText())){
							tf.setText(null);
							return;
						}

						try{
							Long.parseLong(tf.getText().replace("-", "").replace(".", ""));
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
							return;
						}
						
						if(tf.getText().length() > 14){
							type = 2;
						}

						if(type == 1){
							if(tf.getText().length() == 14){
								String cpf = tf.getText().replace(".", "").replace("-", "").replace("/", "");
								cpf = cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(10, 13);
							}
							if(tf.getText().length() == 3 || tf.getText().length() == 7){
								tf.setText(tf.getText() + ".");
							}else if(tf.getText().length() == 11){
								tf.setText(tf.getText() + "-");
							}
						}else{
							String cnpj = tf.getText();
							if(tf.getText().length() > 14 && tf.getText().length() < 15 ){
								cnpj = tf.getText().replace(".", "").replace("-", "");
								cnpj = cnpj.substring(0,2) + "." + cnpj.substring(2,5) + "." + cnpj.substring(5, 8) + "/";
							}
							if(tf.getText().length() == 15){
								cnpj += "-";
							}

							if(cnpj.length() > 19){
								cnpj = cnpj.substring(0, 19);
							}
							tf.setText(cnpj);
						}

						

						tf.positionCaret(tf.getText().length());
					});
				}
			}else if(tipo == 6){
				for (int i = 2; i < parametros.length; i++) {

					TextField tf = ((TextField)parametros[i]);
					tf.setOnKeyReleased(e->{
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						try{
							Long.parseLong(tf.getText().replace(".", "").replace("(", "").replace(")", ""));
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
							return;
						}

						if(tf.getText().length() == 1 ){
							tf.setText("("+tf.getText());
						}else if(tf.getText().length() == 3){
							tf.setText(tf.getText() + ")");
						}else if(tf.getText().length() == 9){
							tf.setText(tf.getText() + ".");
						}


						if(tf.getText().length() > 14){
							tf.setText(tf.getText().substring(0,14));
						}

						tf.positionCaret(tf.getText().length());
					});
					
				}
			}
			else if(tipo==7){
				//Formatação de horas
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = ((TextField)parametros[i]);

					tf.setOnKeyReleased(e->{
						if(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
							|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText())){
								return;
						}
						try{
							Long.parseLong(tf.getText().replace(":", ""));
						}catch(NumberFormatException | NullPointerException e1){
							tf.setText(null);
							return;
						}

						/*if(tf.getText().length() == 2 ){
							tf.setText(tf.getText()+":");
						}else if(tf.getText().length() == 6){
							tf.setText(tf.getText() + ":");
						}*/

						//00:00:00
						/*if(tf.getText().length() > 8){
							tf.setText(tf.getText().substring(0,8));
						}*/

						tf.positionCaret(tf.getText().length());
					});
					tf.focusedProperty().addListener((obs,was,is)->{
						if(!is){
							if(!isNull(tf)){
								if(!tf.getText().contains(":")){
									tf.setText(tf.getText()+":00:00");
								}else{
									char[] b = tf.getText().toCharArray();
									int c = 0;
									for(int n = 0; n < b.length; n ++){
										if(b[n] == ':'){
											c++;
										}
									}
									if(c == 1){
										tf.setText(tf.getText()+":00");
									}
								}
	
								var s = new SimpleDateFormat("HH:mm:ss");
								s.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
								var date = Calendar.getInstance();
						
								try {
									date.clear();
									
							
									date.setTimeInMillis(s.parse(tf.getText()).getTime());
									date.get(Calendar.HOUR);
									long hora = TimeUnit.HOURS.convert((date.getTimeInMillis()), TimeUnit.MILLISECONDS);
									
									String time = hora + ":" + String.format("%02d", date.get(Calendar.MINUTE)) + ":"
									+ String.format("%02d", date.get(Calendar.SECOND));
									tf.setText(time);
	
								} catch (ParseException e) {
									tf.setText(null);
								}
								
							}
							
						}
					});
				}
			}else if(tipo == 8){
				for (int i = 2; i < parametros.length; i++) {
					TextField tf = ((TextField)parametros[i]);
	
					tf.focusedProperty().addListener((obs, wasFocused, isfocused)->{
						if(!isfocused) {
							tf.setText(lostFocus(tf.getText()));
						} 
					});
				
					tf.setOnKeyReleased(e->{
						if(!(e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT 
						|| e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP || Functions.isNull(tf.getText()))) {

							if (tf.getText() == null || tf.getText().isEmpty()) {
								tf.setText(null);
							}else{
								if (Functions.filtro(tf.getText().replaceAll(":", ""))) {
									tf.setText(null);
								}else if (tf.getText().length() > 8) {
									tf.setText(tf.getText().substring(0, 8));
								}else if (tf.getText().length() == 2 || tf.getText().length() == 5) {
									tf.setText(tf.getText() + ":");
								}
								tf.positionCaret(tf.getText().length());
							}
						}
					});
				}
			}
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
		var tipo = "";
		if(lista[1].getClass().equals(String.class)){
			tipo = ((String) lista[1]).toLowerCase();
		}
		
		Object[] p = {"Atenção","",1};
		if (res.equals("string")) {
			p[1] = "Preecha todos os campos!";
			if (tipo.equals("textfield") || tipo.equals("input")) {
				for (int i = 2; i < lista.length; i++) {
					TextInputControl tf = (TextInputControl) lista[i];
					if (isNull(tf.getText())) {
						
						dialogBox(p);
						tf.requestFocus();
						tf.setText(null);
						return true;
					}
				}
			} else {
				for (int i = 2; i < lista.length; i++) {
					var var = (String) lista[i];
					if (isNull(var)) {
						dialogBox(p);
						return true;
					}
				}
			}

		} else if (res.equals("int") || res.equals("double") || res.equals("long")) {
			p[1] = "Preencha os campos corretamente!";
			if (tipo.equals("textfield")) {
				for (int i = 2; i < lista.length; i++) {
					TextInputControl tf = (TextInputControl) lista[i];
					if (isNull(tf.getText()) || (Functions.filtro(tf.getText()) && res.equals("int"))) {
						dialogBox(p);
						tf.requestFocus();
						tf.setText(null);
						return true;
					}else if (res.equals("double")){
						try{
							Double.parseDouble(tf.getText());
						}catch(NumberFormatException e){
							dialogBox(p);
							tf.requestFocus();
							tf.setText(null);
							return true;
						}
					}else if (res.equals("long")){
						try{
							Long.parseLong(tf.getText());
						}catch(NumberFormatException e){
							dialogBox(p);
							tf.requestFocus();
							tf.setText(null);
							return true;
						}
					}
				}
			} else {
				for (int i = 2; i < lista.length; i++) {
					var var = (String) lista[i];
					if ((isNull(var) || Functions.filtro(var)) && res.equals("int")) {
						dialogBox(p);
						return true;
					}else if (res.equals("double")){
						try{
							Double.parseDouble(var);
						}catch(NumberFormatException e){
							dialogBox(p);
							return true;
						}
					}else if (res.equals("long")){
						try{
							Long.parseLong(var);
						}catch(NumberFormatException e){
							dialogBox(p);
							return true;
						}
					}
				}
			}

		} else if (res.equals("combobox")) {
			p[1] = "Preecha todos os campos!";
			int n = 2;
			if(!lista[1].getClass().equals(String.class)){
				n = 1;
			}
			for (int i = n; i < lista.length; i++) {
				@SuppressWarnings("unchecked")
				var cb = (ComboBox) lista[i];
				if (cb.getValue() == null) {
					dialogBox(p);
					cb.requestFocus();
					return true;
				}
			}

		} else if (res.equals("dataehora")) {

			if (tipo.equals("textfield")) {
				p[1] = "Valor inválida ao campo!";
				for (int i = 2; i < lista.length; i++) {
					TextInputControl tf = (TextInputControl) lista[i];
					if (isNull(tf.getText()) || Functions.filtro(tf.getText().replaceAll(":", ""))) {
						dialogBox(p);
						tf.requestFocus();
						tf.setText(null);
						return true;
					}
				}
			} else if (tipo.equals("datepicker")) {
				p[1] = "Selecione uma data!";
				for (int i = 2; i < lista.length; i++) {
					var dt = (DatePicker) lista[i];
					if (dt.getValue() == null
							|| Functions.filtro(dt.getValue().toString().replaceAll("/", "").replaceAll("-", ""))) {
						dialogBox(p);
						dt.requestFocus();
						dt.setValue(null);
						return true;
					}
				}
			}

		} else if(res.equals("checkbox")){
			p[1] = "Selecione ao menos um item!";
			for (int i = 2; i < lista.length; i++) {
				var ck = (CheckBox)lista[i];
				if(ck.isSelected()){
					return false;
				}
			}
			dialogBox(p);
			var ck = (CheckBox)lista[3];
			ck.requestFocus();
			return true;
		}
		else if(res.equals("cpf/cnpj")){

			for (int i = 2; i < lista.length; i++) {
				TextField txtcnpjecpf = (TextField)lista[i];
				if((txtcnpjecpf.getText().length() > 14 && (txtcnpjecpf.getText().contains(".") || txtcnpjecpf.getText().contains("-") || txtcnpjecpf.getText().contains("/"))) || (txtcnpjecpf.getText().length() > 12 && 
				!(txtcnpjecpf.getText().contains(".") || txtcnpjecpf.getText().contains("-") || txtcnpjecpf.getText().contains("/")))){
					p[1] = "Digite um cnpj válido!";
					if(!(Functions.isCNPJ(txtcnpjecpf.getText().replace("-", "").replace(".", "").replace("/", "")))){
						dialogBox(p);
						txtcnpjecpf.requestFocus();
						return true;
					}
					if(!(Functions.isCNPJ(txtcnpjecpf.getText().replace("-", "").replace(".", "").replace("/", "")))){
						dialogBox(p);
						txtcnpjecpf.requestFocus();
						return true;
					}
				}else{
					p[1] = "Digite um cpf válido!";
					if(!(Functions.isCPF(txtcnpjecpf.getText().replace(".", "").replace("-", "")))){
						dialogBox(p);
						txtcnpjecpf.requestFocus();
						return true;
					}
					if(!(Functions.isCPF(txtcnpjecpf.getText().replace(".", "").replace("-", "")))){
						dialogBox(p);
						txtcnpjecpf.requestFocus();
						return true;
					}
				}
			}
		}
		else if(res.equals("cpf")){
			for (int i = 2; i < lista.length; i++) {
				p[1] = "Digite um cpf válido!";
				TextField txtcpf = (TextField)lista[i];
				if(!(Functions.isCPF(txtcpf.getText().replace(".", "").replace("-", "")))){
					dialogBox(p);
					txtcpf.requestFocus();
					return true;
				}
				if(!(Functions.isCPF(txtcpf.getText().replace(".", "").replace("-", "")))){
					dialogBox(p);
					txtcpf.requestFocus();
					return true;
				}
			}
		}else if(res.equals("cnpj")){
			p[1] = "Digite um cnpj válido!";
			for (int i = 2; i < lista.length; i++) {
				TextField txtcnpj = (TextField)lista[i];
				if(!(Functions.isCNPJ(txtcnpj.getText().replace("-", "").replace(".", "").replace("/", "")))){
					dialogBox(p);
					txtcnpj.requestFocus();
					return true;
				}
				if(!(Functions.isCNPJ(txtcnpj.getText().replace("-", "").replace(".", "").replace("/", "")))){
					dialogBox(p);
					txtcnpj.requestFocus();
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
	 * @apiNote Serve para a formatação do objeto ComboBox. Principalmente para o tipo ComboBox<Object> utilizando a classe Objeto 
	 * @param parametros
	 */
	@SuppressWarnings("unchecked")
	public static void formatComboBoxObject(Object[] parametros){
			for(int j = 0; j < parametros.length; j++) {
				ComboBox<Object> c = (ComboBox<Object>) parametros[j];
				c.setConverter(new StringConverter<Object>() {

					@Override
					public String toString(Object object) {
						if (object == null){
							return null;
						} 
						return object.toString();
					}

					@Override
					public Object fromString(String s) {
						if(!isNull(s) && c.getValue() != null){
							
							Objeto o = (Objeto) c.getValue();
							if(s.equals(o.getsFirst(o.toString))){
								return o;
							}else{
								Object[] pc = {c};
								takevaluecombobox(pc);
								if(c.getValue() != null){
									return (Objeto)c.getValue();
								}else{
									return null;
								}
							}
						}else if(!(isNull(s))){
							Object[] pc = {c};
							takevaluecombobox(pc);
							return c.getValue();
						}else{
							return null;
						}
						
							
					}
				});
		
				c.focusedProperty().addListener((obs,wasfocused,isfocused)->{
					Object[] pc = {c};
					takevaluecombobox(pc);
				});


				String[] s = new String[c.getItems().size()];
				for (int i = 0; i < c.getItems().size(); i++) {
					Objeto o = (Objeto)c.getItems().get(i);
					s[i] = o.toString();
				}
			
				new PopUp(c.getEditor(), s);
			
			}
	}

	public static void takevaluecombobox(Object[] parametros){
	
		ComboBox<Object> c = (ComboBox<Object>)parametros[0];

		if(c.getValue() == null && Functions.isNull(c.getEditor().getText())){
			return;
		}

		if(c.getValue() != null && !Functions.isNull(c.getEditor().getText())){
			if(c.getValue().toString().equals(c.getEditor().getText())){
				return;
			}
		}

		for (int i = 0; i < c.getItems().size(); i++) {
			Objeto o = (Objeto)c.getItems().get(i);
			
			for (int j = 0; j < o.valuestosearch.size(); j++) {
				
				if(o.getFirst(o.valuestosearch.get(j)) != null){
					String value = ((String)o.getFirst(o.valuestosearch.get(j))).toLowerCase();
					
					if(value.toLowerCase().trim().equals(c.getEditor().getText().toLowerCase().trim())){
						c.getSelectionModel().select(i);
						c.getEditor().setText(c.getEditor().getText());
						return;
					}
				}
			}
			
			
		}
		c.getEditor().setText(null);
		c.setValue(null);

	}

	public static void takevaluecombobox(ComboBox<Object> c){

		if(c.getValue() == null && Functions.isNull(c.getEditor().getText())){
			return;
		}

		if(c.getValue() != null && !Functions.isNull(c.getEditor().getText())){
			if(c.getValue().toString().equals(c.getEditor().getText())){
				return;
			}
		}

		for (int i = 0; i < c.getItems().size(); i++) {
			Objeto o = (Objeto)c.getItems().get(i);
			
			for (int j = 0; j < o.valuestosearch.size(); j++) {
				
				if(o.getFirst(o.valuestosearch.get(j)) != null){
					String value = ((String)o.getFirst(o.valuestosearch.get(j))).toLowerCase();
					
					if(value.toLowerCase().trim().equals(c.getEditor().getText().toLowerCase().trim())){
						c.getSelectionModel().select(i);
						c.getEditor().setText(c.getEditor().getText());
						return;
					}
				}
			}
			
			
		}
		c.getEditor().setText(null);
		c.setValue(null);

	}

	// Procurando valores no ComboBox
	@Deprecated
    public static void searchinCBeditable(ComboBox<Combobox> comboBox) {
        if (Functions.isNull(comboBox.getEditor().getText())) {
            return;
        }
        // Se não for um numero
        if (Functions.filtro(comboBox.getEditor().getText())) {
            comboBox.getSelectionModel().select(selectCB(comboBox.getEditor().getText(), comboBox));
        } else {// Se for um numero
            comboBox.getSelectionModel()
                    .select(selectIDCB(Integer.parseInt(comboBox.getEditor().getText()), comboBox));
        }
    }
	
	/**
	 * @apiNote Limpa os valores dos objetos do JavaFX.
	 * @param parametros
	 */
	public static void ClearObjects(Object[] parametros){
		for (int i = 0; i < parametros.length; i++) {
			Object obj = parametros[i];
			
			if(obj == null){
				continue;
			}else if(obj.getClass() == TextField.class){
				TextField tf = (TextField)obj;
				tf.setText(null);
			}else if(obj.getClass() == ComboBox.class){
				ComboBox cb = (ComboBox)obj;
				cb.setValue(null);
			}else if(obj.getClass() == CheckBox.class){
				CheckBox ck = (CheckBox)obj;
				ck.setSelected(false);
			}else if(obj.getClass() == TextArea.class){
				TextArea ta = (TextArea)obj;
				ta.setText(null);
			}else if(obj.getClass().equals(String.class)){
				obj = "";
			}else if(obj.getClass().equals(TableView.class)){
				TableView t = (TableView)obj;
				t.getItems().clear();
			}else if(obj.getClass().equals(DatePicker.class)){
				DatePicker dt = (DatePicker)obj;
				dt.setValue(null);
			}
		}
	}	
	/**
	 * @apiNote Bloqueia os objetos do JavaFX.
	 * @param parametros
	 */
	public static void blockbuttons(Object[] parametros){
		boolean block = (boolean)parametros[0];
		for (int i = 1; i < parametros.length; i++) {
			try{
				Node n = (Node) parametros[i];
				n.setDisable(block);
			}catch(Exception e){
				if(parametros[i].getClass().equals(Button.class)){
					Button b = (Button)parametros[i];
					b.setDisable(block);
				}else if(parametros[i].getClass().equals(Tab.class)){
					Tab p = (Tab)parametros[i];
					p.setDisable(block);
				}else if(parametros[i].getClass().equals(TabPane.class)){
					TabPane p = (TabPane)parametros[i];
					p.setDisable(block);
				}else if(parametros[i].getClass().equals(TableView.class)){
					TableView t = (TableView)parametros[i];
					t.setDisable(block);
				}else if(parametros[i].getClass().equals(CheckBox.class)){
					CheckBox ck = (CheckBox)parametros[i];
					ck.setDisable(block);
				}else if(parametros[i].getClass().equals(Pane.class) || parametros[i].getClass().equals(VBox.class)){
					Pane p = (Pane)parametros[i];
					p.setDisable(block);
				}else if(parametros[i].getClass().equals(MenuItem.class)){
					MenuItem mi = (MenuItem)parametros[i];
					mi.setDisable(block);
				}
			}

		}
	}
	/**
	 * @apiNote Bloqueia os objetos do JavaFX, recebendo um Node[] como parametro.
	 * @param block
	 * @param parametros
	 */
	public static void blockbuttons(Boolean block, Node[] parametros){
		for (int i = 0; i < parametros.length; i++) {
			Node n = parametros[i];
			n.setDisable(block);
		}
	}

    
	/***
	 * @apiNote 0 - *Header (String)
	 * @apiNote 1 - *ContentText (String)
	 * @see Função para criar um DialogBox. [*]Campos Obrigatórios
	 * @param
	 * @return
	 */
	public static void dialogBox(Object[] parametros) {
		
		Platform.runLater(()->{
			Stage stage;
			if ((int)parametros[2] == 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Mensagem de Atenção");
				alert.setHeaderText((String)parametros[0]);
				stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(Functions.class.getResourceAsStream("images/Atencao.png")));
				alert.setContentText((String)parametros[1]);
				alert.show();
			} else if ((int)parametros[2] == 2) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Mensagem de Informação");
				alert.setHeaderText((String)parametros[0]);
				stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(Functions.class.getResourceAsStream("images/information.png")));
				alert.setContentText((String)parametros[1]);
	
				alert.show();
			}
		});
		
	}

	/***
	 * @apiNote 0 - *Header (String)
	 * @apiNote 1 - *ContentText (String)
	 * @apiNote 2 -  ButtonType Focus 
	 * @see Função para criar um ConfirmationDialog. [*]Campos Obrigatórios
	 * @param
	 * @return
	 */
	public static ButtonType ConfirmationDialog(Object[] parametros) {
		
		Stage stage;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText((String)parametros[0]);
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Functions.class.getResourceAsStream("view/Icons/confirm.png")));
		alert.setContentText((String)parametros[1]);
		if(parametros.length >= 3){
			Button cancel = (Button)alert.getDialogPane().lookupButton(ButtonType.CANCEL);
			Button ok = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
			if(parametros[2] == ButtonType.OK){
				ok.setDefaultButton(true);
				cancel.setDefaultButton(false);
			}else{
				cancel.setDefaultButton(true);
				ok.setDefaultButton(false);
			}

		}
		Optional<ButtonType> result = alert.showAndWait();
		//result.i
		return result.get();
		
	}

	public static String InputDialog(String Header,String text,int format){
		TextInputDialog td = new TextInputDialog();
		
		Stage stage;
		td.setTitle(Header);
		td.setHeaderText(text);
		if(format != 0){
			Object[] p = {1,format,td.getEditor()};
			formatRelased(p);
		}
		
		stage = (Stage) td.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Functions.class.getResourceAsStream("confirm.png")));
		

		Optional<String> result = td.showAndWait();
		return result.get();
	}

	// Seletor de combobox, ele pesquisa se o nome do item tem em alguns dos itens
	// do cb.
	public static int selectCB(String item, ComboBox<Combobox> cb) {

		if (!(item == null || item.isEmpty())) {
			for (int i = 0; i <= cb.getItems().size(); i++) {

				if (item.toUpperCase().equals(cb.getItems().get(i).getNome().toUpperCase())) {
					return i;
				}else if(cb.getItems().get(i).getDadoextra() != null){
					if(item.toUpperCase().equals(cb.getItems().get(i).getDadoextra().toUpperCase())){
						return i;
					}
				}
			}
		}
		return -1;
	}

	public static int selectIDCB(int id, ComboBox<Combobox> combo) {
		int i = -1;
		if (!(id == 0)) {
			for (i = 0; i < combo.getItems().size(); i++) {
				if (combo.getItems().get(i).getId() == id) {
					return i;
				}
			}
		}

		return -1;
	}

	public static int selectCBObjeto(String first, String equals, ComboBox<Object> combobox){

		for (int j = 0; j < combobox.getItems().size(); j++) {
            Objeto pro = (Objeto)combobox.getItems().get(j);
            if(pro.getFirst(first).toString().toLowerCase().equals(equals.toLowerCase())){
                combobox.getSelectionModel().select(j);
				return j;
            }
        }
		return -1;

	}

	public static TextField releasedHora(TextField hora) {
		if (hora.getText() == null || hora.getText().isEmpty()) {
			hora.setText(null);
			return hora;
		}
		if (Functions.filtro(hora.getText().replaceAll(":", ""))) {
			hora.setText(null);
			return hora;
		}

		if (hora.getText().length() > 8) {
			hora.setText(hora.getText().substring(0, 8));
		}

		if (hora.getText().length() == 2 || hora.getText().length() == 5) {
			hora.setText(hora.getText() + ":");

		}

		hora.positionCaret(hora.getText().length());

		return hora;
	}

	/***
	 * @deprecated use o do functions (formatDatePicker)
	 * @param data
	 * @return
	 */
	public static DatePicker releasedDate(DatePicker data) {
		if(data.getEditor().getText() == null || data.getEditor().getText().isEmpty()){
			return data;
		}
		if (Functions.filtro(data.getEditor().getText().replaceAll("/", ""))) {
			data.getEditor().setText(null);
			return data;
		}

		if (data.getEditor().getText().length() > 10) {
			data.getEditor().setText(data.getEditor().getText().substring(0, 10));
		}

		if (data.getEditor().getText().length() == 2 || data.getEditor().getText().length() == 5) {
			data.getEditor().setText(data.getEditor().getText() + "/");

		}

		data.getEditor().positionCaret(data.getEditor().getText().length());

		return data;
	}

	public static String lostFocus(String hora) {

		if (hora == null || hora.isEmpty()) {
			return null;
		}

		if (Functions.filtro(hora.replaceAll(":", ""))) {
			return null;
		}
		switch (hora.length()) {
			case 2:
				hora += ":00:00";
				break;
			case 3:
				hora += "00:00";
				break;
			case 4:
				hora += "0:00";
				break;
			case 5:
				hora += ":00";
				break;
			case 6:
				hora += "00";
				break;
			case 7:
				hora += "0";
				break;

		}
		if (hora.length() == 3) {
			hora = hora + "00:00";

		} else if (hora.length() == 6) {
			hora = hora + "00";
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
		try {
			c.setTime(s.parse(hora));

			hora = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":"
					+ String.format("%02d", c.get(Calendar.MINUTE)) + ":"
					+ String.format("%02d", c.get(Calendar.SECOND));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return hora;

	}

	public static DatePicker dateFormat(DatePicker d) {
		d.setConverter(new StringConverter<LocalDate>() {
			String pattern = "dd/MM/yyyy";
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			{
				d.setPromptText(pattern);
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
		return d;
	}
    		
}
