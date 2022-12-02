/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author redne
 */
public class GerirBD {
    
    private ArrayList<User> lista = null;
    
    
    public GerirBD(){
        lista = new ArrayList<>();
    }
    
    public int totalUser()
    {
        return this.lista.size();
    }

    
    public Boolean inserirUSer(String nome, String password){
        //pesquisar o utilizar na lista, para saber se está registado
        User u = findUser(nome, password);
        
        //se não encontrou
        if(u == null){
            u = new User(nome, password);
            lista.add(u);
            return true;
        }
        
        return false;
    }
    
    public User findUser(String nome, String password){
        //se a lista estiver vazia
        if(lista.isEmpty())
            return null;
        
        for (int i = 0; i < lista.size(); i++) {
            //se for o user a procurar
            if(lista.get(i).equals(nome, password))
                return lista.get(i);
            
        }
        
        return null;
    }

    public User getUser(int i) {
        return lista.get(i);
    }
    
    public void setCanalSaida(PrintStream saidaDados, String nome, String password){
        //sabemos que o user existe, atualizamos o canal de saida
        findUser(nome, password).setCanalSaida(saidaDados);
        
    }
}
