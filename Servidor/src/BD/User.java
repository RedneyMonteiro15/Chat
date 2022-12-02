/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.io.PrintStream;

/**
 *
 * @author redne
 */
public class User {
    
    private String nome, password;
    
    private PrintStream saidaDados = null; //canal de saida do user;
    
    private Boolean online = false;
    
    public User(String nome, String password){
        this.nome = nome;
        this.password = password;
    }
    
    public Boolean equals(String nome, String password){
        return this.nome.equalsIgnoreCase(nome) && this.password.equalsIgnoreCase(password);
    }

    public String getNome() {
        return this.nome;
    }
    
    public void setCanalSaida(PrintStream saidaDados){
        this.saidaDados = saidaDados;
    }
    
    public PrintStream getCanalSaida(){
        return this.saidaDados;
    }
    
    public boolean getOnline(){
        return this.online;
    }
    
    public void setOnline(boolean online){
        this.online = online;
    }
}

