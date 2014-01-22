/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gpstracker;

/**
 *
 * @author Dinal
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/* 
 * @author PING64/Vimukthi Weerasiri
 */
public class TestProtocol {
    public static long T;
    public static long temp;
    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(4480);
        System.out.println("Server started at " + new SimpleDateFormat().format(new Date()));
        System.out.println("waiting for a connection...");
        T = System.currentTimeMillis();
        temp = T;
        Socket connectionSocket = welcomeSocket.accept();
        System.out.println(connectionSocket.getInetAddress().toString() + " connected" + interval());
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        //start reading
        new ReadDevice(inFromClient).start();
        //defining what to send for the device
        while (true) {
            char c = (char) System.in.read();
            if (c == 'l') {
                outToClient.writeBytes("LOAD");
                System.out.println("Sent LOAD" + interval());
            } else if (c == 'o') {
                outToClient.writeBytes("ON");
                System.out.println("Sent ON" + interval());
            } else if (c == 't') {
                System.out.println("NOW" + interval());
            } else if (c == 'p') {
                outToClient.writeBytes("**,imei:863070014296916,B");
                System.out.println("Sent request" + interval());
            } else if (c == 'q') {
                outToClient.writeBytes("**,imei:863070014296916,C,10s");
                System.out.println("Sent request" + interval());
            } else if (c == 'w') {
                outToClient.writeBytes("**,imei:863070014296916,B");
                System.out.println("Sent request" + interval());
            }
        }
    }
    //calculating the time interval
    public static String interval() {
        long i = System.currentTimeMillis();
        String s = " after " + (((i - T) * 1.0) / 1000) + " seconds and " + (((i - temp) * 1.0) / 1000) + " seconds after last interaction";
        temp = i;
        return s;
    }
    //Thread to read data whatever coming form device
    public static class ReadDevice extends Thread {
        BufferedReader IN;
        public ReadDevice(BufferedReader in) {
            IN = in;
        }
        @Override
        public void run() {
            try {
                String s = "";
                int i;
                i = IN.read();
                while (i > 0) {
                    System.out.print((char) i);
                    s += (char) i;
                    if ((char) i == ';') {
                        System.out.println();
                        System.out.println(s + interval());
                        s = "";
                    }
                    i = IN.read();
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}