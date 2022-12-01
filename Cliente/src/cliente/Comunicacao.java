/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

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
public class Comunicacao {
    
    private Socket ligacao;
    
    public void ligarServidor(String username, String senha){
        
        try {
            Socket ligacao = new Socket("127.0.0.1", 1000);
            
            BufferedReader entradaDados = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
            
            PrintStream saida = new PrintStream(ligacao.getOutputStream());
            
            
            saida.println(username);
            saida.println(senha);
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Comunicacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
