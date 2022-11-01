package com.functions.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @apiNote Classe objeto para abstrair qualquer classe.
 * @author kauan reis
 */
public class Objeto {

    private HashMap<String,List<Object>> map = new HashMap<>();
    private String toString;
    private List<String> valuestosearch = new ArrayList<>();

    public Objeto() {
    }

    public List<Object> get(String name_column) {
       
        return map.get(name_column.toLowerCase());
  
    }

    public void set(String name_column, List<Object> valores){
        map.put(name_column.toLowerCase(),valores);
    }

    public Object getFirst(String name_column) {
       
        return map.get(name_column.toLowerCase()).get(0);

    }

    public String getsFirst(String name_column){
        try{
            String value = (String)map.get(name_column).get(0);
            return value;
        }catch(NullPointerException e){
            return "";
        }
        
    }

    public void set(String name_column, String Value){
        List<Object> l = map.get(name_column);
        if(l == null || l.isEmpty()){
            l = new ArrayList<>();
        }
        l.add(Value);
        map.put(name_column.toLowerCase(), l);
    }

    public Object[] getKeys(){
       return map.keySet().toArray();
    }

    public String toString(){
        return toString;
    }

    public void setHashMap(HashMap<String,List<Object>> map){
        this.map = map;
    }

    public HashMap<String,List<Object>> getHashMap(){
        return map;
    }

    public void setToString(String toString){
        this.toString = toString;
    }
    
    public void setValuesToSearch(List<String> values){
        valuestosearch = values;
    }

    public List<String> getValuesToSearch(){
        return valuestosearch;
    }
}
