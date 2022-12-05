package servidor;

import BD.GerirBD;
import BD.User;
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
    
    private static GerirBD gerirBD = null;
    
    
    
    
    public Servidor(Socket ligacao){
        this.ligacao = ligacao;
    }

    public static void main(String[] args) {
        
        try {
            ServerSocket processo = new ServerSocket(1000);
            System.out.println("Servidor ligado!!!\n");
            
            gerirBD = new GerirBD();
            gerirBD.inserirUSer("redney", "1234");
            gerirBD.inserirUSer("alanah", "teste");
            gerirBD.inserirUSer("tatiana", "taty");
            
            
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
            String nick, password;
            
            nick = entradaDados.readLine();
            password = entradaDados.readLine();
            User u;
            
            //enquanto as credenciais não existirem na lista
            while ((u = gerirBD.findUser(nick, password))==null) {                
                saidaDados.println("<NI>");
                
                nick = entradaDados.readLine();
                password = entradaDados.readLine();
            }
            
            //atualizar o canal de saida do user
            gerirBD.setCanalSaida(saidaDados, nick, password);
            
            //user online
            u.setOnline(true);
            
            
            //enviar uma mensagem para o cliente de boas vinda
            saidaDados.println("<welcome>Bem vinda ao nosso chat");
            
            
            //enviar quem está online
            enviarOnline(saidaDados, nick);
            System.out.println("Total de user: " + gerirBD.totalUser());
            
            
            System.out.println("Recebi: " + nick + " - " + password);
            
            boolean ligado =true;
            String msg;
            while(ligado){
                //receber msg do user
                msg = entradaDados.readLine();
                
                
                if(msg.equalsIgnoreCase("<exit>")){
                    ligado = false;
                } else{
                    //se for mensagem publica
                    if(msg.startsWith("<PUB>")){
                        enviarMsgPUB(msg, nick);
                    } else if(msg.startsWith("<PRIV>")){
                        
                        enviarMsgPRIV(msg.substring(msg.indexOf("#")+1, msg.length()), 
                                nick, msg.substring(msg.indexOf(">")+1, msg.indexOf("#")));
                    }
                }
                
                
                
                
                System.out.println("Recebi do User: " + msg);
            }
            
            //o user já não esta online
            u.setOnline(false);
            
            //envia uma mensgam para informar que já saíu
            enviarMsg("<exit>" + nick + " saiu da chat...");
            
            
            
            saidaDados.close();
            entradaDados.close();
            ligacao.close();
        } catch (IOException e) {
        }
        
    }
    
    public void enviarOnline(PrintStream saida, String nick)
    {
        for (int i = 0; i < gerirBD.totalUser(); i++) {
            if(!nick.equalsIgnoreCase(gerirBD.getUser(i).getNome()))
                saida.println("<U>" + gerirBD.getUser(i).getNome());
        }
    }
    
    public void enviarMsgPUB(String msg, String nick){
        for (int i = 0; i < gerirBD.totalUser(); i++) {
            if(gerirBD.getUser(i).getOnline()){
                if(!nick.equalsIgnoreCase(gerirBD.getUser(i).getNome()))
                    gerirBD.getUser(i).getCanalSaida().println("<PUB>" + nick +": " + msg.substring(msg.indexOf(">")+1, msg.length()));
                else 
                    gerirBD.getUser(i).getCanalSaida().println("<PUB>:" + msg.substring(msg.indexOf(">")+1, msg.length()));

            } 
        }
    }
    
    public void enviarMsg(String msg){
        for (int i = 0; i < gerirBD.totalUser(); i++) {
            if(gerirBD.getUser(i).getOnline())
                gerirBD.getUser(i).getCanalSaida().println(msg);
            
                
        }
    }

    private void enviarMsgPRIV(String msg, String nick, String dest) {
        for (int i = 0; i < gerirBD.totalUser(); i++) {
            if(gerirBD.getUser(i).getOnline()){
                if(dest.equalsIgnoreCase(gerirBD.getUser(i).getNome())){
                    gerirBD.getUser(i).getCanalSaida().println("<PRIV>"+dest+"#"+nick+": " + msg);
                } else if(nick.equalsIgnoreCase(gerirBD.getUser(i).getNome())){
                    gerirBD.getUser(i).getCanalSaida().println("<PRIV>"+dest+"#: " + msg);
                }
             }
        }
        System.out.println("Destino: " + dest);
    }
    
    
    public static void inserirUser(String nome, String password){
        gerirBD.inserirUSer(nome, password);
        System.out.println("Total: " + gerirBD.totalUser());
    }

}
