package com.functions.models;

import java.util.ArrayList;
import java.util.List;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.control.TextField;

/**
 * @apiNote Classe para TextField javaFX
 * @author kauan reis
 */
public class PopUp {
    private static List<AutoCompletionBinding<String>> listaauto = new ArrayList<>();
    private static List<TextField> listatf = new ArrayList<>();

    public PopUp(TextField tf, List<String> lista){
        int i = verifyAuto(tf);
        if(i != -1){
            var auto = listaauto.get(i);
            auto.dispose();
            auto = TextFields.bindAutoCompletion(tf, lista);
            listaauto.set(i, auto);
        }else{
            AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(tf, lista);
            listaauto.add(auto);
            listatf.add(tf);
        }
    }

    public PopUp(TextField tf, String[] lista){
        int i = verifyAuto(tf);
        if(i != -1){
            var auto = listaauto.get(i);
            auto.dispose();
            auto = TextFields.bindAutoCompletion(tf, lista);
            listaauto.set(i, auto);
        }else{
            AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(tf, lista);
            listaauto.add(auto);
            listatf.add(tf);
        }
    }

    public int verifyAuto(TextField tf){
        if(!listaauto.isEmpty()){
            for (int i = 0; i < listatf.size(); i++) {
                if(listatf.get(i).equals(tf)){
                    return i;
                }
            }
            return -1;
        }else{
            return -1;
        }
    }
    
}
