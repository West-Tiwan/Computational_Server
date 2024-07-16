import java.net.*;
import java.io.*;
import java.util.function.Consumer;

public class Server extends Thread {
    //initialize calculator
    static Calculator calc = new Calculator();

    public Consumer<Socket> getConsumer(){
        return (clientSocket)->{
            try {
                PrintWriter pr = new PrintWriter(clientSocket.getOutputStream(),true);
                try {
                    System.out.println("connection established");

                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());

                    while (true){
                        Query query = (Query) objIn.readObject();
                        float result = handleChoice(query);
                        pr.println("ready");
                        pr.println(result);
                        System.out.println(result);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
//                pr.close();
//                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void main(String[] args) {

        Server server = new Server();
        try{
            //connecting
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("server running - waiting for client...");

            while (true){
                Socket acceptedSocket = ss.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
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
