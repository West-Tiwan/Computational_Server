import java.net.Socket;
import java.io.*;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Client {
    public static void main(String[] args) {
        try {
            Socket soc = new Socket("localhost", 3000);
            System.out.println("client running");
            PrintWriter pr = new PrintWriter(soc.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));

            // Example of sending a message to the server
            pr.println(55);

            // Wait for a response from the server
            String response = br.readLine();
            System.out.println("Server says: " + response);

            // Close resources
            br.close();
            pr.close();
            soc.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
