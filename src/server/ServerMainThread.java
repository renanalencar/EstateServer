package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * This file is a driver for the grade service. Clients may ask to read a grade
 * from one of the students, and the information is sent back to the requester.
 * Communication happens via a socket connection.
 *
 * IMPORTANT: the server must be able to find the class of the objects that it
 * receives via the socket connection. For instance, to use the Estate object
 * that is part of the Android side of this application, we must run the server
 * with the correct path to that class, e.g.: java -cp
 * ".:/Users/ata/Documents/workspace/AP3/bin/" server.ServerMainThread
 *
 * @author Fernando
 *
 */
public final class ServerMainThread {

  /**
   * Private constructor to avoid instantialization of this class. This class is
   * not meant to generate objects.
   */
  private ServerMainThread() {
  }

  /**
   * The main method of the server side of the database application.
   * 
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    System.out.println("Running the server 6");

    // O objeto Map vai simular um banco de dados em que
    // vai armazenar um id do tipo Long um objeto Serializable
    Map<Long, Serializable> database = new HashMap<Long, Serializable>();
    // instancia um objeto do tipo ServerSocket
    ServerSocket serverSocket = null;
    // criar uma flag para ficar "ouvindo" o endereço host e a porta
    // que estão no serverSocket
    boolean listening = true;
    try {
      // inicializa o serverSocket passando a porta através do DAO.PORT
      serverSocket = new ServerSocket(DAO.PORT);
      // Enquanto o servidor estiver "ouvindo" faça...
      while (listening) {
        // Cria uma nova thread que gerencia a conexão
        // e o banco de dados
        new ConnectionHandlerThread(serverSocket.accept(), database).start();
      }
      // ao fianlizar a thread é necessário fechar a conexão
      serverSocket.close();
    } catch (IOException e) {
      // caso dê algum erro, imprimir o erro
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
