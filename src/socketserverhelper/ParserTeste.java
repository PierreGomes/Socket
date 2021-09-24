package socketserverhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;

public class ParserTeste {
    /*
    * # Auxilia na recuperação de dados a String do request
    */
    /*Expressão regular pra recuperar o corpo da requisição em JSON*/
    public static final String JSONBODYREGEX= ".*\\{.*\\}";
    
    /*Variáveis auxiliares*/
    private String RequestString;
    private String[] RequestString_Array;
    private String Request_FirstLine;
    
    /*Delimitadores pra fazer o parse na String*/
    private String FirstLineDelimiter;
    private String Delimiter;
    
    /*Informações de conexão local*/
    private String RequestMethod;
    private Boolean hasBody = false;
    private Boolean hasOrigin;
    private String Body;
    private String Content_Type;
    
    /*HashMap pra recuperar informações do request*/
    private Map<String, String> map;
    
    /*Variável auxiliar pra tratamento do JSON no formato String*/
    private JSONObject jObject = null;
    
    private boolean error = false;
    
    ParserTeste(String request, String FirstLineDelimiter, String Delimiter){
        error = !sync(request, FirstLineDelimiter, Delimiter);
        
        if(error)
            return;

        ParseRequestMethod();

        this.getMappedRequest();
    }

    boolean sync(String request, String FirstLineDelimiter, String Delimiter){
        /*
        *   # Inicializa as variáveis locais e adianta possíveis erros
        */

        /*Validações do String*/
        if(request.isEmpty()){
            log("String Vazia!");
            return false;
        }
        /*Variáveis locais*/
        try{
            /*Divide a string*/
            this.RequestString_Array = splitString(request, Delimiter);
            /*Inicializa as variáveis locais: Strings do request, primera linha do request, delimitador da primeira linha, e delimitador do restante da requisição*/
            this.RequestString = request;
            this.Request_FirstLine = this.RequestString_Array[0];
            this.FirstLineDelimiter = FirstLineDelimiter;
            this.Delimiter = Delimiter;
            return true;
        }
        catch (Exception e){
            log("Erro ao Inicializar.");
            return false;
        }
    }

    public String getRequestMethod(){
        return this.RequestMethod;
    }

    public String[] splitString(String string, String delimiter){
        /*
        *   # Modularização do método split da classe String conforme Parâmetros: String, delimiter
        * */
        return string.split(delimiter);
    }

    public void ParseRequestMethod(){
        /*  # Divide a string
        *   1 - Recupera as informações da primeira linha: Método da requisição
        *   2 - Seta a variável local
        *   3 - Remove a primeira linha da String
        *   # Return: void
        * */
        /*Método é a primeira informação na requisição*/
        int methodPosition = 0;

        /*Divide a primeira linha e armazena o método da requisição*/
        String[] firstLineSplited = this.Request_FirstLine.split(this.FirstLineDelimiter);
        this.RequestMethod = firstLineSplited[methodPosition];

        /*Remove a primeira linha da string*/
        this.RequestString = RequestString.replace(this.Request_FirstLine+"\n", "");
        this.RequestString_Array = splitString(this.RequestString, this.Delimiter);
    }

    public Map<String, String> getMappedRequest(){
        /*
        *   # Recebe a string com a requisição e retorna HashMap
        *   1 - Itera a String_Array divida por linhas(Itera as linhas da requisição)
        *   2 - Divide as linhas com delimitador = ":" e recupera os dados (Campos do Header "name: value")
        *   3 - Valida campos mais importantes: Origin(URL) e Body(JSON).
        *   4 - Adiciona campo no HashMap.
        * */
        map = new HashMap<String, String>();

        /*Percorre a requisição*/
        for(String RequestString_Array : RequestString_Array){
            try{
                /*Divide a linha*/
                String name = RequestString_Array.split(":")[0];
                String value = RequestString_Array.split(":")[1];

                /*Validação do Origin*/
                if(RequestString_Array.split(":").length == 3){
                    boolean hasOrigin = true;
                    value = RequestString_Array.split(":")[1] +":"+RequestString_Array.split(":")[2];
                }
                /*Validação do Body*/
                if( !hasBody && name.equals("Content-Type")){
                    hasBody = true;
                    this.Content_Type = value;
                }
                /*Se chegou na linha do Body*/
                if(hasBody && getRegEx(RequestString_Array, JSONBODYREGEX)){
                    name = "body";
                    value = RequestString_Array;
                    this.Body = value;
                }
                /*Adiciona ao HashMap*/
                map.put(name, value);
            }catch(Exception e){
                /*Log pra debugar*/
                log("Erro ao mapear a requisição.");
            }
        }
        /*Retorna HashMap*/
        return map;
    }

    private void log(String s) {
        System.out.println("ParseRequestStringHelper:"+ s);
    }

    /*Encapsulamento das variáveis locais*/
    public String[]  getArray(){
        return this.RequestString_Array;
    }
    public Map getMap(){
        return this.map;
    }
    public String getBody(){
        return this.Body;
    }
    public boolean getHasBody(){
        return this.hasBody;
    }
    public String getContentType(){
        return this.Content_Type;
    }
    public String getMethod(){
        return this.RequestMethod;
    }
    public boolean getError(){
        return this.error;
    }

    /*Utils*/
    public boolean getRegEx(String text, String RegEx){
        /*
        *   # Busca algo dentro de uma string a partir de uma expressão regular
        * */
        Pattern pattern = Pattern.compile(RegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public String getStringJson(String JSON, String key){
        /*
         *Considera como input um objeto JSON(String): "{(Conteúdo)}"
         */
        String value = "";

        try{
            jObject = new JSONObject(JSON);
            value = jObject.getString(key);
        }catch(Exception e){
            log("Erro ao ler String JSON");
            try{
                value = String.valueOf(jObject.getInt(key));
            }catch(Exception exc){log("Erro ao ler valor inteiro do JSON");}
        }

        return value;
    }

    public String getJsonArray(String JSON, String key, int index) {
        /*
         *Considera como input um objeto JSON(String): "{(Conteúdo)}"
         */
        String value = "";

        try{
            jObject = new JSONObject(JSON);
            JSONArray jArray = jObject.getJSONArray(key);
            value = jArray.get(index).toString();
        }catch(Exception e){
            log("Erro ao ler JSONArray.");
        }
        return value;
    }

    public String getTemplate(String JSON){
        return null;
    }
}