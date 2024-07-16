import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try{
            //connecting
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("server running - waiting for client...");
            Socket soc = ss.accept();
            System.out.println("connection established");

            BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            Scanner sc = new Scanner(soc.getInputStream());
            PrintWriter pr = new PrintWriter(soc.getOutputStream(),true);

            while (true){
                String query = br.readLine();
                pr.println("ready");
                pr.println(query);
                System.out.println(query);
            }

            //closing resources
//            pr.close();
//            br.close();
//            soc.close();
        }
        catch (IOException error){
            System.out.println(error.toString());
        }
    }

    public static class calculator{

        public float add(float a,float b){
            return (a+b);
        }

        public float sub(float a,float b){
            return (a-b);
        }

        public float multi(float a,float b){
            return (a*b);
        }

        public float div(float a,float b){
            return (a/b);
        }

        public float mod(float a,float b){
            return (a%b);
        }
    }
}
