package http.io;

import java.util.Scanner;

public class SystemIo implements Io {
  public String in() {
    Scanner scan = new Scanner(System.in);
    return scan.next();
  }

  public void out(String message) {
    System.out.println(message);
  }

}
