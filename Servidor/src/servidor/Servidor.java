package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread{
    
    private Socket ligacao = null;
    
    public Servidor(Socket ligacao){
        this.ligacao = ligacao;
    }

    public static void main(String[] args) {
        
        try {
            ServerSocket processo = new ServerSocket(1000);
            System.out.println("Servidor ligado!!!\n");
            
            
            while(true){
                Socket ligacao = processo.accept();
                System.out.println("Alguém se ligou no servidor.\n");
                
                Thread t = new Servidor(ligacao);
                t.start();
                
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run()
    {
        try {
            BufferedReader entradaDados = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
            
            PrintStream saidaDados = new PrintStream(ligacao.getOutputStream());
            
            Boolean validUser = true;
            do {                
                
            } while (validUser);
            String nick = entradaDados.readLine();
            String senha = entradaDados.readLine();
            
            if(nick.equalsIgnoreCase("redney") && senha.equalsIgnoreCase("1234")){
                saidaDados.println("ok");
            } else{
                saidaDados.println("no");
            }
            
            System.out.println("Recebi: " + nick + " - " + senha);
            
            
            saidaDados.close();
            entradaDados.close();
            ligacao.close();
        } catch (IOException e) {
        }
        
    }
    
}
