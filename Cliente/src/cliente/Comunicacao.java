/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redne
 */
public class Comunicacao extends Thread{
    
    private Socket ligacao;
    private static Login janelaLogin = null;
    private static Janela janela = null;
    private BufferedReader entrada = null;
    private PrintStream saida = null;
    
    public Comunicacao(Login janela){
        this.janelaLogin = janela;
    }
    
    public Comunicacao(BufferedReader entrada){
        this.entrada = entrada;
    }
    
    public void ligarServidor(String username, String senha){
        
        try {
            
            
            Socket ligacao = new Socket("127.0.0.1", 1000);
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
            
            saida = new PrintStream(ligacao.getOutputStream());
            
            
            saida.println(username);
            saida.println(senha);
            
            Thread t = new Comunicacao(entrada);
            t.start();
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Comunicacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setJanel(Janela janela)
    {
         this.janela = janela;
         janelaLogin = null;
    }
    
    @Override
    public void run(){
        
        while (true) {            
            try {
                if (janelaLogin != null) {
                    janelaLogin.receberDados(entrada.readLine());
                }
                if(janela != null){
                    janela.receberDados(entrada.readLine());
                }

            } catch (IOException ex) {
                Logger.getLogger(Comunicacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void enviarMsgPUB(String msg) {
        
        saida.println("<PUB>" + msg);
    }
    
    public void enviarMsgPRIV(String msg, String nick) {
       saida.println("<PRIV>"+nick + "#" + msg);
    }

    public void desligarServidor() {
        
        
        try {
            //fechar tudo
        
            saida.println("<exit>");

            saida.close();
            entrada.close();
            ligacao.close();
        } catch (IOException ex) {
            Logger.getLogger(Comunicacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
