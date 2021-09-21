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
    
    private final String iniciandoSocket = "\n\n===================Iniciando Socket Nº: ";
    private final String respondendoSocket = "\n\n=================Respondendo";
    private final String lendoRequest = "\n\n=================Lendo request";
    private final String fechandoSocket = "\n\n=================Fechando Socket";
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
   
   void log(String msg){
       System.out.println(msg+"====================");
   }

    private void startListeningPort() throws InterruptedException{
        
        try{
            /*Inicia servidor na porta indicada*/
            serverSockt = new ServerSocket(port);
            
            while(true){
                count++;
                log(iniciandoSocket + count);
                
                /*Aceita Request*/
                sockt = serverSockt.accept();
                
                log(lendoRequest);
                String request = getRequestToString();
                parser = new ParseRequestStringHelper(request, "/", "\n");
                System.out.println(request);
                
                log(respondendoSocket);
                System.out.println(reponse());
                
                log(fechandoSocket);
                pout.close();
                isr.close();
                br.close();
                sockt.close();
            }
        }catch (IOException e) {
           e.printStackTrace();
       }
        
        
    }
    
    
    private String reponse() throws IOException {
        try{
            /*Identifica o método do request*/
            String method = parser.getRequestMethod();
            /*Responde 200 ok por padrão*/
            String response = OK;
            /*Se for o OPTIONS responde com o Response Header*/
            if(method.equals("OPTIONS ")){
                String responseHeader = getResponseHeader();  
                response += responseHeader;
            }

            /*Metódos de resposta ao request*/
            pout = new PrintWriter(sockt.getOutputStream());
            pout.write(response);
            return response;
        }catch(Exception e){
            log("erro ao responder request");        
            return "";
        }
        
    }
    public String getResponseHeader(){
        try{
            String resourceURL = this.parser.getMappedRequest().get("Origin");
            String ACAllowHeaders = this.parser.getMappedRequest().get("Access-Control-Request-Headers");
            String ACAllowMethods = this.parser.getMappedRequest().get("Access-Control-Request-Method");
            
            String responseHeader =         "Access-Control-Allow-Origin:" +resourceURL+"\n"+
                                            "Access-Control-Allow-Headers:"+ ACAllowHeaders+"\n" +
                                            "Access-Control-Allow-Methods:"+ ACAllowMethods+"\n" ;
            return responseHeader;
        }catch(Exception e){
            log("erro ao construir Response Header");
            return "";
        }
    }

    private String getRequestToString() throws InterruptedException {
        /*
        *Lê o Buffer do Request e transforma em String
        */
        if(sockt != null){
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
                return receivedMessage;
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }
        return "failed";
        
    }


}