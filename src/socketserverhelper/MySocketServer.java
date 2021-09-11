package socketserverhelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.Scanner;


/*Quando implementado no Android
*Importar do android.os.Handler*;
*
*import java.util.logging.Handler;
*/
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MySocketServer {
    
   private int port;
   
   private Socket sockt;
   private ServerSocket serverSockt;
   private InputStreamReader isr;
   private BufferedReader br;
   private PrintWriter pout;
   private String receivedMessage;
   
   public MySocketServer(int port){
       this.port = port;
       
       startListeningPort();
   }

    private void startListeningPort() {
        //Inicia-se o servidor escutando na porta determinada
        try{
            //Instancia-se o servidor
            serverSockt = new ServerSocket(port);
            
            //loop
            while(true){
                //===========================
                System.out.println("-- Iniciando Socket");
                //===========================
                //Inicia-se a conexão
                sockt = serverSockt.accept();
                
                //===========================
                System.out.println("-- Respondendo Request");
                //===========================
                //Responde o request
                reponse();
                
                //===========================
                System.out.println("-- Iniciando leitura do request");
                //===========================
                //Transforma o Request p/ String
                getRequestToString();
                
                //Fecha conexão do socket
                //===========================
                System.out.println("-- Fechando Conexão do Socket");
                //===========================
            }
        }catch (IOException e) {
           e.printStackTrace();
       }
        
        
    }
    
    //Não ta funcionando (ver depois)
    private void reponse() throws IOException {
        String responseCode;
        pout = new PrintWriter(sockt.getOutputStream());
        
        //Verifica a conexão
        Boolean connected = sockt.isConnected();
        
        if(sockt != null && connected){
            responseCode = "200 OK";
        }else{
            //Se não está conectado
            responseCode = "404 failed";
        }
        
        pout.write(responseCode);
    }

    private void getRequestToString() {
        if(sockt != null){
            //======================================
            System.out.println("--INICANDO LEITURA DO REQUEST");
            //======================================
            receivedMessage = new String();
            
            try{
                //Metodos e Objetos para leitura do conteudo do request 
                isr = new InputStreamReader(sockt.getInputStream());
                br = new BufferedReader(isr);
                
                //===================================
                while(br.ready())
                   receivedMessage += "\n"+ br.readLine();
                      
                System.out.println("Saiu do loop");
                System.out.println(receivedMessage);
                sockt.close();
                //===================================
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }
        
    }

    private void TesteSaida(String s) {
        System.out.println("Received Data:\n" + s);
    }
    
    
}
