/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpstracker;

/**
 *
 * @author Dinal
 */

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String args[]) throws Exception {


        ServerSocket welcomeSocket = new ServerSocket(4480);


        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println(connectionSocket.getInetAddress().toString());
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            //readIT(inFromClient);  
            //System.out.println(inFromClient.readLine());
            Thread.sleep(2000);
            outToClient.writeBytes("LOAD");
            //System.out.println("came");
            Thread.sleep(3000);
            // readIT(inFromClient);
            outToClient.writeBytes("ON");
            Thread.sleep(1000);
            outToClient.writeBytes("ON");
            Thread.sleep(1000);
            outToClient.writeBytes("ON");
            Thread.sleep(1000);
            outToClient.writeBytes("ON");
            // readIT(inFromClient);
            //  Thread.sleep(5000);
            Thread.sleep(1000);
            //outToClient.writeBytes("**,imei:359586018966098,B");
            Thread.sleep(1000);
            readIT(inFromClient);
            //again            
            readIT(inFromClient);
            System.out.println(inFromClient.readLine());
            //outToClient.writeBytes("ON"+'\n');    
            System.out.println(inFromClient.readLine());
            System.out.println(inFromClient.readLine());
            System.out.println(inFromClient.readLine());
            String data;
            while (!inFromClient.ready() && ((data = inFromClient.readLine()) != null)) {
                System.out.println(data);
            }
        }
    }

    public static void readIT(BufferedReader in) throws IOException {
        int c = 5;
        while (c > 0) {
            c = in.read();
            System.out.print((char) c);
        }
        in.close();
        System.out.println("aaa");
    }
}
