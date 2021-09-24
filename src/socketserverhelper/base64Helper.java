package socketserverhelper;
import java.util.Base64;

public class base64Helper {
    public String getDecodedString(String encoded){
        try{
            return new String(Base64.getDecoder().decode(encoded.getBytes()));
        }catch(Exception e){ System.out.println("erro ao decodificar string");}
        return null;
    }
    public String getEncodedString(String decoded){
        try{
            return Base64.getEncoder().encodeToString(decoded.getBytes());
        }catch(Exception e){ System.out.println("erro ao codificar string");}
        return null;
    }
    
}
