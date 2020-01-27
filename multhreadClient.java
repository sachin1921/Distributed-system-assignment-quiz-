import java.io.*;
import java.net.*;
import java.util.Scanner;

public class multhreadClient{

   public static void main(String[] args) throws IOException{
       
       try{

           Scanner scan = new Scanner(System.in);

           InetAddress ip = InetAddress.getByName("127.0.0.1");

           Socket socket = new Socket(ip, 7689);

           DataInputStream data_input_stream = new DataInputStream(socket.getInputStream());
           DataOutputStream data_output_stream = new DataOutputStream(socket.getOutputStream());

    
           int question_num = data_input_stream.read();
           System.out.println(data_input_stream.readUTF());
           String option = scan.nextLine();
           data_output_stream.writeUTF(option);
           System.out.println("\n");
              
            for (int i = 0; i <= question_num - 1; i++)
           {

            System.out.println(data_input_stream.readUTF());
               String answer = scan.nextLine();
               data_output_stream.writeUTF(answer);

                if(answer.equalsIgnoreCase("Exit")){
                   System.out.println("Closing this connection : " + socket);
                   socket.close();
                   System.out.println("Connection closed");
                   break;
               }

               else if(answer.equalsIgnoreCase("Hint")){
               	System.out.println(data_input_stream.readUTF());
               	answer = scan.nextLine();
               	data_output_stream.writeUTF(answer);
               }


            System.out.println(data_input_stream.readUTF()); 
           }

           System.out.println("Your score is: " + data_input_stream.read() + " / " + question_num);

           scan.close();
           data_input_stream.close();
           data_output_stream.close();
           System.out.println("Closing connection : " + socket);
           socket.close();
           System.out.println("Connection closed");

       }catch(Exception e){
           e.printStackTrace();
       }
   }
}