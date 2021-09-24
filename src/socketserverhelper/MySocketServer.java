package socketserverhelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MySocketServer {
   /*OK response*/
   private final String OK = "HTTP/1.1 200 OK\n";
   private final String ERROR = "HTTP/1.1 500 INTERNAL SERVER ERROR\n";
   
   /*Variáveis de conexão*/
   private int port;
   private Socket socket;
   private ServerSocket serverSocket;
   private boolean erro = false;
   /*Variáveis de leitura do request*/
   private InputStreamReader inputSR;
   private BufferedReader buffRead;
   private PrintWriter printOut;
   /*Variáveis Auxiliares*/
   private String requestString;
   private String response;
   private String method;
   private String resourceURL;
   private String body;
   private Boolean hasBody = false;
   private Map<String, String> requestMap;
   /*Contagem de conexões*/
   private int count = 0;
   
   /*Parser Helper*/
   private ParseRequestStringHelper parser;
   
   public MySocketServer(int port) throws InterruptedException{
       this.port = port;
       startListeningPort();
   }
   
   void log(String msg){
       String log = msg;
       System.out.println(log);
   }

    private void startListeningPort() throws InterruptedException{
        /*
        * # Inicia conexão com a porta parametrizada
        * # Funciona como um listener para request que chegam
        * # 1 - Abre conexão na porta indicada
        * # 2 - Aceita request(espera até chegar alguma) 
        * # 3 - (getRequestToString())Lê o request(Buffer --> String)
        * # 4 - Inicializa o ParseRequestStringHelper com a String o request
        * # 5 - (sync())Sincroniza as variáveis da classe com os dados da conexão efetuada no momento
        * # 6 - (response())Responde o request
        * # 7 - Fecha os métodos de leitura e a conexão do socket
        *
        * Parâmetros(Locais):
        *  - serverSocket: ServerSocket (Local)
        *  - socket: Socket (Local)
        *  - count: contador de conexões efetuadas durante a execução
        *  - requestString: String - request lido por loop
        *  - Métodos de leitura
        *
        * # return: void
        */
        try{
            
            /* #1 Inicia servidor na porta indicada*/
            serverSocket = new ServerSocket(port);

            while(true){
                /*Contador++*/
                count++;
                /*Log*/
                log("\nINICIANDO SOCKET " + count);
                /* #2 Aceita Request*/
                socket = serverSocket.accept();
                
                /*Log*/
                log("LENDO REQUEST");
                /* #3 Lê o buffer da conexão*/
                sleep(500);
                getRequestToString();
                
                /* #4 Nova Instância do parserHelper a cada conexão*/
                parser = new ParseRequestStringHelper(requestString, "/", "\n");
                
                /*Log*/
                log("SINCRONIZANDO COM DADOS DA REQUISIÇÃO");
                /* #5 Sincroniza a classe com os dados da conexão efetuada*/
                erro = sync();
                
                /*Log do request recebido(Inteiro: header e body)*/                
                log(this.requestString);
                
                /*Log*/
                log("RESPONDENDO");
                /* #6 Responde o request*/
                response();
                /*Log do response enviado*/
                log(response);
                
                /*Log*/
                log("FINALIZANDO SOCKET");
                /* #7 Finaliza*/
                printOut.flush();
                printOut.close();
                inputSR.close();
                socket.close();
                sleep(2000);
                if(erro){
                    debug();
                    sleep(2000);
                }
                
                cleanTerminal();
            }
        }catch (IOException e) {
           e.printStackTrace();
       }
        
        
    }
    
    
    private void getRequestToString() throws InterruptedException {
        /*
        * #3 Lê o Buffer do Request e transforma em String
        *
        * Parâmetros(Locais):
        *   - (Preenchido por esse método)requestString: lido do buffer da conexão
        *   - inputSr(Local)
        *   - buffRead(Local)
        *   - socket(Local)
        * # Return: void
        */
        if(socket != null){
            /*Instancia nova String para o request ao ler*/
            requestString = new String();
            try{
                /*Métodos de leitura do request*/ 
                inputSR = new InputStreamReader(socket.getInputStream());
                buffRead = new BufferedReader(inputSR);
                
                /*StringBuilder: Monta a String Char por Char*/
                StringBuilder content = new StringBuilder();
                /*Valor do Char(Inteiro)*/
                int value;
                
                /*Inicia leitura*/
                while(buffRead.ready()){
                    /*Lê um char*/
                    value = buffRead.read();
                    /*Concatena o valor no String Builder*/
                    content.append((char)value);
                }
                /*Preenche a variável local com o resultado*/
                requestString = content.toString();
            }catch(IOException e){
                /*Em caso de erro em tempo de execução*/
                e.printStackTrace();
                log("Erro ao ler o request");
            }
            
        }
        
    }
    
    private boolean sync() {
        /*
        * #5 Syncroniza Varíaveis locais com os dados da conexão
        *   - requestMap
        *   - method
        *   - resourceURL
        *   - hasbody
        *   - body
        * # Validação de erros
        */
        
        /*Sinaliza erro na recuperação de dados da requisição*/
        if(parser.getError())
            return true;
        
        try{
            /*requestMap*/
            this.requestMap = parser.getMap();
            /*method*/
            this.method = parser.getMethod();
            /*hasBody*/
            this.hasBody = parser.getHasBody();
            /*body*/
            if(this.hasBody)
                this.body = parser.getBody();
            else
                this.body = "Empty";
            /*Origin/ResourceURL(CORS)*/
            if(this.requestMap.containsKey("Origin"))
                this.resourceURL = requestMap.get("Origin");
            return false;
        }catch(Exception e){
            log("Erro de conexão!");
            return true;
        }
    
    }
    
    private void response() throws IOException {
        /*
        * #6 Responde o request
        *   - Validação de erros
        */
        if(erro)
            response = ERROR;
        else{
            response = OK;
            /*Adiciona Header do Response*/
            response += getResponseHeader();
        }
        try{
            /*Metódos de resposta ao request*/
            printOut = new PrintWriter(socket.getOutputStream());
            /*Responde o request*/
            printOut.write(response);
        }catch(Exception e){
            log("erro ao responder request");
        }
        
        
    }
    
    public String getResponseHeader(){
        /*
        * #6.1 Monta a String do Header(Response)
        *
        *   Identifica qual método da requisição: OPTIONS/POST?
        *       - POST: Responde: 200 OK + Access-Control-Allow-Origin: (resourceURL) + JSON(Resposta de Impressão)
        *       - OPTIONS: Responde: 200 OK + Todos os parâmetros "Access-Control"(Que estiverem no header da requisição)
        *
        *   Parâmetros do Header da requisição a serem postos no response Header:
        *       - Origin(POST e OPTIONS).
        *       - Access-Control-Request-Headers(OPTIONS).
        *       - Access-Control-Request-Method(OPTIONS).
        */
        try{
            /*Parâmetro padrão*/
            String header = "Access-Control-Allow-Origin:" + this.resourceURL;
            /*Se POST*/
            if(this.method.contains("POST"))
                return header;
            
            /*Restante dos Parâmetros Access-Control(OPTIONS)*/
            String ACAllowHeaders = this.parser.getMappedRequest().get("Access-Control-Request-Headers");
            String ACAllowMethods = this.parser.getMappedRequest().get("Access-Control-Request-Method");
            
            /*Concatena na String do Header(OPTIONS)*/
            header +=   "\nAccess-Control-Allow-Headers:"+ ACAllowHeaders+"\n" +
                        "Access-Control-Allow-Methods:"+ ACAllowMethods+"\n" ;
            
            /*Retorna*/
            return header;
        }catch(Exception e){
            /*Em caso de erro em tempo de execução*/
            log("erro ao construir Response Header");
            return "";
        }
    }

    private void cleanTerminal() {
        for(int i=0; i<100;i++)
            System.out.print("=");
    }

    private void debug() throws IOException {
        socket = new Socket();
        serverSocket.close();
        serverSocket = new ServerSocket(port);
        log("Reiniciando Socket");
    }
}