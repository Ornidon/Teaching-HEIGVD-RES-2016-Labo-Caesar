package ch.heigvd.res.caesar.protocol;

import java.util.Random;

/**
 *
 * @author Olivier Liechti
 */
public class Protocol {

  public static final int A_CONSTANT_SHARED_BY_CLIENT_AND_SERVER = 42;

  public static String encrypt(String message, int key){
    char[] msg = new char[message.length()];
    for(int i = 0; i < message.length(); ++i){
      msg[i] = (char) (message.charAt(i) + key);
    }
    return String.valueOf(msg);
  }

  public static String decrypt(String message, int key){
    char[] msg = new char[message.length()];
    for(int i = 0; i < message.length(); ++i){
      msg[i] = (char) (message.charAt(i) - key);
    }
    return String.valueOf(msg);
  }

  public static int generateEncryptingKey(){
    Random r = new Random();
    return Math.abs(r.nextInt()%5);
    /* modulo 5 pour ne pas sortir de la table ascii, nous n'avons pas cherché
    un algorithme parfait. Le but étant de simplement tester que plusieurs
    client avec des clés différentes peuvent communiquer en parralèle avec le serveur*/
  }
}
