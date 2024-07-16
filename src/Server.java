import java.net.*;
import java.io.*;

public class Server extends Thread {
    //initialize calculator
    static Calculator calc = new Calculator();

    public static void main(String[] args) {
        try{
            //connecting
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("server running - waiting for client...");
            Runnable work = ()->{
                try {
                    Socket soc = ss.accept();
                    System.out.println("connection established");

                    BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    PrintWriter pr = new PrintWriter(soc.getOutputStream(),true);
                    ObjectInputStream objIn = new ObjectInputStream(soc.getInputStream());

                    while (true){
//                String query = br.readLine();
                        Query query = (Query) objIn.readObject();
                        float result = handleChoice(query);
                        pr.println("ready");
                        pr.println(result);
                        System.out.println(result);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            };

            Thread t1 = new Thread(work);
            Thread t2 = new Thread(work);
            Thread t3 = new Thread(work);
            t1.start();
            t2.start();
            t3.start();

            //closing resources
//            pr.close();
//            br.close();
//            soc.close();
        }
        catch (IOException error){
            System.out.println(error.toString());
        }
    }

    static float handleChoice(Query query){
        int choice = query.getChoice();
        float a = query.getA();
        float b = query.getB();
        if (choice == 1){
            return calc.add(query.getA(), query.getB());
        } else if (choice == 2) {
            return calc.sub(a, b);
        } else if (choice == 3) {
            return calc.multi(a, b);
        } else if (choice == 4) {
            return calc.div(a, b);
        } else if (choice == 5) {
            return calc.mod(a, b);
        } else {
            return -1;
        }
    }
}
