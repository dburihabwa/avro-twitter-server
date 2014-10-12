package com.burihabwa.cal.avro;

import org.apache.avro.ipc.Server;

import java.io.IOException;

/**
 * Created by dorian on 10/12/14.
 */
public class Main {

    public static void main(String [] args) {
        int portNumber = 42000;
        Server server = null;
        try {
            server = new FastTweetServer(portNumber);
            server.start();
            System.out.println("Server started on port " + portNumber);
            while (true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }
}
