package socketserverhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SocketServerHelper {
    
    public static void main(String[] args) throws InterruptedException {
        new MySocketServer(12222);
    }
    
}
