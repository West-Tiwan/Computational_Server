import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("server running");
            Socket soc = ss.accept();
            System.out.println("connection established");
            BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            int in = Integer.parseInt(br.readLine());
            System.out.println(in);
            PrintWriter pr = new PrintWriter(soc.getOutputStream(),true);
            pr.println("received :"+in);
        }
        catch (IOException error){
            System.out.println(error.toString());
        }
    }
}
