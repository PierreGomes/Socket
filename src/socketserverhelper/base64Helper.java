package socketserverhelper;
import java.util.Base64;

public class base64Helper {
    /*
    public static void main(String[] args) {

        String textoOriginal = "esta é uma string de teste para serialização/deserialização em Base64";

        System.out.println("Texto original: " + textoOriginal);

        String textoSerializado = Base64.getEncoder().encodeToString(textoOriginal.getBytes());

        System.out.println("Texto em Base64: " + textoSerializado);

        String textoDeserializado = new String(Base64.getDecoder().decode(textoSerializado));

        System.out.println("Texto deserializado: "+ textoDeserializado);

    }
    */
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
