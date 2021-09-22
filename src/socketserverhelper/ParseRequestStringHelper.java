package socketserverhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;

public class ParseRequestStringHelper {
    /*
    *Receives request String and parses for HashMap with key mapped params 
    */
    public static final String JSONBODYREGEX= ".*\\{.*\\}";
            
    private String RequestString;
    private String[] RequestString_Array;
    private String RequestMethod;
    private String RequestHeader_FirstLine;
    
    private String FirstLineDelimiter;
    private String Delimiter;
    
    private Boolean hasBody = false;
    private Boolean hasOrigin;
    private String Body;
    private String Content_Type;
    
    private Map<String, String> map;
    
    private JSONObject jObject = null;
    
    ParseRequestStringHelper(String request, String FirstLineDelimiter, String Delimiter){
        /*"Corta" a String linha por linha (A principio o buffer manda a requisição assim)*/
        String[] reqStringArray = splitString(request, Delimiter);
        
        /*Validações do String*/
        if(request.isEmpty()){
            System.out.println("String do request vazia!");
            return;
        }
        this.FirstLineDelimiter = FirstLineDelimiter;
        this.Delimiter = Delimiter;
            
        /*Seta variáveis locais*/
        this.RequestString_Array = reqStringArray;
        this.RequestString = request;
        this.RequestHeader_FirstLine = RequestString_Array[0];
        this.RequestMethod = ParseRequestMethod();
        
        this.getMappedRequest();
    }
    
    public String getRequestMethod(){
        return this.RequestMethod;
    }
    
    public String[] splitString(String string, String delimiter){
        return string.split(delimiter);
    }
    
    public String ParseRequestMethod(){
        /*Considera que a primeira coisa que vem no request é o método*/
        int methodPosition = 0;
        
        /*Pega a primeira linha da String e remove
        *-->Necessário Retirar a primeira Linha da String
        *-->Mapper ta previsto pra ler só o restante da string
        */
        String[] firstLineSplited = this.RequestHeader_FirstLine.split(this.FirstLineDelimiter);
        String method = firstLineSplited[methodPosition];
        
        /*Tira a primeira linha da string local do request*/
        this.RequestString = RequestString.replace(this.RequestHeader_FirstLine+"\n", "");
        /*Atualiza a array de strings do request*/    
        this.RequestString_Array = splitString(this.RequestString, this.Delimiter);
        
        
        return method;
    }
    
    public Map<String, String> getMappedRequest(){
        map = new HashMap<String, String>();
        for(String RequestString_Array : RequestString_Array){
            try{
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
                
                /*Verifica se chegou no Body*/
                if(hasBody && getRegEx(RequestString_Array, JSONBODYREGEX)){
                    name = "body";
                    value = RequestString_Array;
                    this.Body = value;
                }
                
                //System.out.println("Debug Mapper: " + name + ": " +value);
                
                map.put(name, value);
                
            }catch(Exception e){
                //LogCat no android
                System.out.println("Erro ao fazer o parse da string(Body)");
            }
        }
        return map;
    }
    public String[]  getArray(){
        return this.RequestString_Array;
    }
    public Map  getMap(){
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
    public boolean getRegEx(String text, String RegEx){
        Pattern pattern = Pattern.compile(RegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
    
    public String getStringJson(String JSON, String key){
        /*
        *Considera como input um objeto JSON(String): "{(Conteúdo)}"
        */
        String value = "";
        jObject = new JSONObject(JSON);
                
        try{
            value = jObject.getString(key);
        }catch(Exception e){
            value = String.valueOf(jObject.getInt(key));
            //e.printStackTrace();
            System.out.println("Erro na captura da String JSON");
        }
        
        jObject.clear();
        
        return value;
    }

    public String getJsonArray(String JSON, String key, int index) {
        /*
        *Considera como input um objeto JSON(String): "{(Conteúdo)}"
        */
        String value = "";
        jObject = new JSONObject(JSON);
                
        try{
            JSONArray jArray = jObject.getJSONArray(key);
            value = jArray.get(index).toString();
            System.out.println(value);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Erro na captura do Array JSON");
        }
        jObject.clear();
        
        return value;
    }
    
    public String getTemplate(String JSON){
        return null;
    }
}
