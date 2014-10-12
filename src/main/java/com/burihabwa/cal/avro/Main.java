package com.burihabwa.cal.avro;

import org.apache.avro.ipc.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by dorian on 10/12/14.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String [] args) {
        int portNumber = 42000;
        Server server = null;
        try {
            server = new FastTweetServer(portNumber);
            server.start();
            System.out.println("Server started on port " + portNumber);
            System.out.println("Type in \"Ctrl + d\" to stop the server");
            while (scanner.hasNextLine()) {}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }
}
