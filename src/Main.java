import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) throws UnsupportedEncodingException {

    int portIndex = Arrays.asList(args).indexOf("-p");
    int directoryIndex = Arrays.asList(args).indexOf("-d");

    Server theServer = new Server(args[portIndex+1], args[directoryIndex+1]);
    theServer.startServer();
  }
}
