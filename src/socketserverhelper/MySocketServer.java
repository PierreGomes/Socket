package socketserverhelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer {
    
    private final String RESPONSEHEADERS = 
                                            "Access-Control-Allow-Origin: https://ah.we.imply.com \n" +
                                            "Access-Control-Allow-Headers: Content-Type\n" +
                                            "Access-Control-Allow-Methods: POST\n" +
                                            "Access-Control-Expose-Headers: Content-Type, Cache-Control, Content-Length, Authorization\n" ;
    
   private int port;
   
   private final String OK = "HTTP/1.1 200 OK\n";
   
   private Socket sockt;
   private ServerSocket serverSockt;
   private InputStreamReader isr;
   private BufferedReader br;
   private PrintWriter pout;
   private String receivedMessage;
   
   private int count = 0;
   
   private ParseRequestStringHelper parser;
   private boolean ok = true;
   
   public MySocketServer(int port) throws InterruptedException{
       this.port = port;
       startListeningPort();
   }

    private void startListeningPort() throws InterruptedException{
        //Inicia-se o servidor escutando na porta determinada
        try{
                //Instancia-se o servidor
                serverSockt = new ServerSocket(port);
            
            //loop
            while(true){
                count++;
                //===========================
                System.out.println("\n\n========================================Iniciando Socket Nº: " + count +"====================================");
                //===========================
                //Inicia-se a conexão
                sockt = serverSockt.accept();
                
                //===========================
                System.out.println("-- Respondendo Request do IP: " + sockt.getRemoteSocketAddress());
                //===========================
                reponse();
                
                //===========================
                System.out.println("-- Iniciando leitura do request");
                //===========================
                parser = new ParseRequestStringHelper(getRequestToString(), "/", "\n");
                System.out.println(getResponseHeader(parser.getRequestMethod()));
                //Fecha conexão do socket
                //===========================
                System.out.println("-- Fechando Conexão do Socket");
                pout.close();
                isr.close();
                br.close();
                sockt.close();
                
                System.out.println(this.getResponseHeader(parser.getRequestMethod()));
                //===========================
            }
        }catch (IOException e) {
           e.printStackTrace();
       }
        
        
    }
    
    public String getResponseHeader(String method){
        
        String resourceURL = this.parser.getMappedRequest().get("Origin");
        
        if(method.equals("OPTIONS") && ok){
            return resourceURL;
        }
        System.out.println(resourceURL);
        return "failed";
    }
    
    //Não ta funcionando (ver depois)
    private void reponse() throws IOException {
        pout = new PrintWriter(sockt.getOutputStream());
        
        System.out.println("\nResponse:\n"+OK + RESPONSEHEADERS);
        pout.write(OK + RESPONSEHEADERS);
    }

    private String getRequestToString() throws InterruptedException {
        /*
        *Lê o Buffer do Request e transforma em String
        */
        if(sockt != null){
            //======================================
            System.out.println("==INICANDO LEITURA DO REQUEST");
            //======================================
            
            receivedMessage = new String();
            
            try{
                //Metodos e Objetos para leitura do conteudo do request 
                isr = new InputStreamReader(sockt.getInputStream());
                br = new BufferedReader(isr);
                
                
                StringBuilder content = new StringBuilder();
                int value;
                while(br.ready()){
                    value = br.read();
                    content.append((char)value);
                }
                
                receivedMessage = content.toString();
                System.out.println("\n\n====Dados recebidos:\n" + content.toString());
                
                
                /* Para ler o request byte por byte não aplicavel
                DataInputStream reader = new DataInputStream(sockt.getInputStream());
                String s ="-";
                
                while(reader.available() > 0){
                    byte b = reader.readByte();
                    System.out.println(b+" ");
                    
                }
                */
                
                //===================================
                System.out.println("==Fim da leitura do request");
                //===================================
                return receivedMessage;
                
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }
        return "failed";
        
    }

    
    
}