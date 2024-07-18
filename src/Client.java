import java.net.Socket;
import java.io.*;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Client {
    public static void main(String[] args) {
        try {
            Socket soc = new Socket("localhost", 3000);
            System.out.println("client running\n");
            PrintWriter pr = new PrintWriter(soc.getOutputStream(), true);
            ObjectOutputStream objOut = new ObjectOutputStream(soc.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            Scanner sc = new Scanner(System.in);
            Query query = new Query();

            while (true){
                //taking input from user
                System.out.print("Choose what you want to do:\n    1.Add\n    2.Subtract\n    3.Multiply\n    4.Divide\n    5.Modulo\n\nEnter your choice: ");
                int choice = sc.nextInt();
                query.setChoice(choice);
                System.out.print("\n\nEnter first number: ");
                float a = sc.nextFloat();
                query.setA(a);
                System.out.print("\n\nEnter second number: ");
                float b = sc.nextFloat();
                query.setB(b);

                //serialization of query obj
                objOut.reset();
                objOut.writeObject(query);
                objOut.flush();

                //waiting for response
                String indicator = br.readLine();
                if ("ready".equals(indicator)){
                    String response = br.readLine();
                    System.out.println("\n\n Server says result is: " + response + "\n\n");
                } else {
                    indicator = br.readLine();
                    while (!"ready".equals(indicator)){
                        indicator = br.readLine();
                        if ("ready".equals(indicator)){
                            break;
                        }
                    };
                }

                //terminating loop
                System.out.println("Do you want to continue?\n    1. Yes\n    2. No");
                int loop = sc.nextInt();
                if (loop == 2){
                    break;
                }
            }

            // Close resources
            br.close();
            objOut.close();
            pr.close();
            soc.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
