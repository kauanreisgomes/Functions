package com.functions.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.functions.FunctionsFX;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @apiNote Classe para escolha de arquivos em javaFX. 
 */
public class FileChoose {
    private File file;

    public FileChoose(){}
   
    public FileChoose(String message,FileChooser extension,Stage stage){
        FileChooser chooser = new FileChooser();
		chooser.setTitle(message);
		chooser.getExtensionFilters().addAll(extension.getExtensionFilters());
		file = chooser.showOpenDialog(stage);
    }

    public FileChoose(String message,FileChooser.ExtensionFilter extension,Stage stage){
        FileChooser chooser = new FileChooser();
		chooser.setTitle(message);
        FileChooser.ExtensionFilter extFilter = extension;
        chooser.setSelectedExtensionFilter(extFilter);
        chooser.getExtensionFilters().addAll(extFilter);
		file = chooser.showOpenDialog(stage);
    }

    public List<File> FileChoosers(String message,FileChooser.ExtensionFilter extension,Stage stage){
        FileChooser chooser = new FileChooser();
		chooser.setTitle(message);
        FileChooser.ExtensionFilter extFilter = extension;
        chooser.setSelectedExtensionFilter(extFilter);
        chooser.getExtensionFilters().addAll(extFilter);
		return chooser.showOpenMultipleDialog(stage);
    }

    public File getFile(){
        return file;
    }


    public void setFile(File file){
        this.file = file;
    }

    public boolean CopyFile(String outputfilelocation){
        if(file.exists()){
            try{
                File f = new File(outputfilelocation);
                if(!f.exists()){
                    f.createNewFile();
                }
                InputStream is =  new FileInputStream(file);
                OutputStream output = new FileOutputStream(outputfilelocation);
                byte[] buffer = new byte[is.available()];   


                is.read(buffer);
                output.write(buffer);
                is.close();
                output.close();
                FunctionsFX.dialogBox("Arquivo salvo com sucesso!", 2);
                return true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                FunctionsFX.dialogBox("Erro ao fazer o upload do arquivo!", 1);
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                FunctionsFX.dialogBox("Erro ao fazer o upload do arquivo!", 1);
                e.printStackTrace();
                return false;
            }
        }else{
            FunctionsFX.dialogBox("Sem arquivo selecionado!", 1);
            return false;
        }
    }

    public void CopyFile(InputStream InputFile, OutputStream outputfile){
            try{
               
                InputStream is =  InputFile;
                OutputStream output = outputfile;
                byte[] buffer = new byte[is.available()];   


                is.read(buffer);
                output.write(buffer);
                is.close();
                output.close();
                FunctionsFX.dialogBox("Arquivo salvo com sucesso!", 2);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                FunctionsFX.dialogBox("Erro ao fazer o upload do arquivo!", 1);
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                FunctionsFX.dialogBox("Erro ao fazer o upload do arquivo!", 1);
                e.printStackTrace();
            }
    }

}
