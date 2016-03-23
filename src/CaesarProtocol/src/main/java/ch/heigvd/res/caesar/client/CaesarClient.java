package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarClient {

  private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());

  private int port;
  Socket socket;
  BufferedReader in = null;
  PrintWriter out = null;
  private int key;

  public CaesarClient(int port) {

    try {
      this.port = port;
      socket = new Socket("localhost", port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
      sendFirstMessage();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void sendFirstMessage() {
    try {
      for (int i = 0; i < 3; i++) {
        LOG.info(in.readLine());
      }
      this.key = Integer.valueOf(in.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean sendMessage(String message) {
    out.println(Protocol.encrypt(message, key));
    if (message.equalsIgnoreCase("bye")) return false;
    else return true;
  }

  public void receptMessage() {
    String line = "";
    try {
      line = in.readLine();
      LOG.info("message chiffré   > " + line);
      LOG.info("message déchiffré > " + Protocol.decrypt(line, key));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getKey(){
    return this.key;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Client > %5$s%n");
    LOG.info("Caesar client starting...");
    LOG.info("Protocol constant: " + Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);

    CaesarClient c = new CaesarClient(Protocol.A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER);
    Scanner s = new Scanner(System.in);

    LOG.info("Key: " + c.key);

    while (c.sendMessage(s.nextLine())) {
      c.receptMessage();
    }

  }

}
