package com.functions.models;

import java.text.ParseException;

import com.functions.FunctionsFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * @apiNote Classe de carregamento para a tela javaFX
 */
public class Loading {
    private Stage stage;
	private Thread th;
    private Timeline timeline;
    private Label loading;
    private ProgressBar loader_bar;
    private Object[] objectsblock;

    public Loading(Stage stage, Thread th,Label loading, ProgressBar loader_bar, Timeline timeline){
        this.stage = stage;
        this.th = th;
        this.timeline = timeline;
        this.loader_bar = loader_bar;
        this.loading = loading;
        Object[] p = {loading,loader_bar};
        timelineload(p);
    }

    public Loading(Stage stage,Label lbloading, ProgressBar loader_bar){
        this.stage = stage;
        this.th = new Thread();
        this.timeline = new Timeline();
        this.loader_bar = loader_bar;
        this.loading = lbloading;
        Object[] p = {lbloading,loader_bar};
        timelineload(p);
    }

    public Loading(Stage stage,Label lbloading, ProgressBar loader_bar, Object[] blocks){
        this.stage = stage;
        this.th = new Thread();
        this.timeline = new Timeline();
        this.loader_bar = loader_bar;
        this.loading = lbloading;
        Object[] p = {lbloading,loader_bar};
        objectsblock = new Object[blocks.length+1];
        for (int i = 0; i < blocks.length; i++) {
            objectsblock[i+1] = blocks[i];
        }
     
        timelineload(p);
    }
    

    /**
	 * @apiNote 0 - boolean (is loading?)
	 * @apiNote 1 - Thread
	 * @apiNote 2 - Stage
	 * @apiNote 3 - VBox
	 * @apiNote 4 - Timeline
	 * @param parametros
	 */
	private void loadingparametros(Object[] parametros){
		Stage s = (Stage)parametros[0];
		//Thread th = (Thread)parametros[1];
		boolean b = (boolean)parametros[2];
		Timeline timeline = (Timeline)parametros[3];
        Platform.runLater(()->{
            Scene scene = s.getScene();
            Platform.setImplicitExit(false);
            
           
            if(b){
                
                scene.setCursor(Cursor.WAIT);
                isVisible(true);
                s.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                            Object[] msg = {"Atenção","Para evitar problemas espere o processo terminar para fechar a tela!", 1};
                            FunctionsFX.dialogBox(msg);
                            event.consume();                       
                    }
                });
                timeline.play();
            }else{
        
                timeline.pause();
                scene.setCursor(Cursor.DEFAULT);
                s.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        s.close();
                    }
                });
                isVisible(false);
            }
        });
    }

    public void loader(boolean load){
            Object[] ploading = {stage,th,load,timeline};
            loadingparametros(ploading);
            if(objectsblock != null && objectsblock.length > 0){
                objectsblock[0] = load;
                try{
                    Node[] parametros = (Node[])objectsblock;
                    FunctionsFX.blockbuttons(parametros);
                }catch(Exception e){
                    FunctionsFX.blockbuttons(objectsblock);
                }
            }
    }

    public boolean ThreadIsAlive(){
        if(th.isAlive()){
            return true;
        }else{
            return false;
        }
    }

    public void startThread(Runnable r){
        th = new Thread(r);
        th.start();
       
        loader(true);
    }

	public Timeline timelineload(Object[] p){
		Label lbloading = (Label)p[0];
        ProgressBar pb = (ProgressBar)p[1];
        pb.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
		KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
            if(lbloading.getText().length() == 10 || lbloading.getText().length() == 11 || lbloading.getText().length() == 12){
                lbloading.setText(lbloading.getText() + ".");
            }else if(lbloading.getText().length() == 13){
                lbloading.setText(lbloading.getText().substring(0,10));
            } 

            if(!th.isAlive()){
                loader(false);
                timeline.stop();
            }
        });
        timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
		return timeline;
	}

    public void isVisible(boolean visible){
        loader_bar.setVisible(visible);
        loading.setVisible(visible);
    }
    
    public void setClose(Object[] parametros){

    }
}
