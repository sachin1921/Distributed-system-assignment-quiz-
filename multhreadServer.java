import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class multhreadServer{
   public static void main(String[] args) throws IOException{
    
       ServerSocket serverSocket = new ServerSocket(7689);

       while (true){
           Socket server = null;

           try{
               server = serverSocket.accept();
               System.out.println("A client has connected");
               DataInputStream data_input_stream = new DataInputStream(server.getInputStream());
               DataOutputStream data_output_stream = new DataOutputStream(server.getOutputStream());
               System.out.println("Assigned a new thread for this client");
               Thread t = new ClientHandler(server, data_input_stream, data_output_stream);
               t.start();
           }catch (Exception e){
               server.close();
               e.printStackTrace();
           }
       }
   }
}


class ClientHandler extends Thread{
   final Socket server;
   final DataInputStream data_input_stream;
   final DataOutputStream data_output_stream;
   String[] Questions = {"Which one is not an operating system ?\na)DOS\nb)MAC \nc)C \nd)Linux", 
   "Which company developed the Mac OS ? \na)Microsoft\nb)Google \nc)IBM \nd)Apple", 
   "What is a firewall on a computer used for ? \na)Security\nb)Transmission \nc)Monitoring \nd)Authentication", 
   "Which programming language is used exclusively for AI ?\na)Java\nb)Python \nc)JavaScript \nd)Prolog",
"How many bits are used in IPv6 address ? \na)32 bits\nb)64 bits \nc)128 bits \nd)256 bits",
"Which one is the first search engine on the internet ?\na)Yahoo\nb)Google \nc)Bing \nd)Archie",
"Which one is the first web browser ?\na)Mosiac\nb)Nexus \nc)Internet Explorer \nd)Safari",
"What was the first computer virus called ?\na)Rabbit\nb)Creeper \nc)Spider \nd)SCA",
"Which one is not a database management software ?\na)COBOL\nb)PostgresSQL \nc)mySQL \nd)Oracle",
"How many layers does the OSI model have ?\na)7 \nb)8 \nc)9 \nd)10",
};

String[] Questions2 = {"Brass gets discoloured in air because of the presence of which of the following gases in air? \n a) Oxygen \n b) Hydrogen sulphide \n c) Carbon dioxide \n d) Nitrogen",
"Which of the following is a non metal that remains liquid at room temperature? \n a) Phosphorus \n b) Bromine \n c) Chlorine \n d) Helium",
"Chlorophyll is a naturally occurring chelate compound in which central metal is \n a) Copper \n b) Magnesium \n c) Iron \n d) Calcium",
"Which of the following is used in pencils? \n a) Graphite \n b) Silicon \n c) Charcoal \n d) Phosphorus",
"Which of the following metals forms an amalgam with other metals? \n a) Tin \n b) Mercury \n c) Lead \n d) Zinc",
"The gas usually filled in the electric bulb is \n a) Nitrogen \n b) Hydrogen \n c) CO2 \n d) Oxygen",
"Washing soda is the common name for \n a) Sodium Carbonate \n b) Calcium BiCarbonate \n c) Sodium BiCarbonate \n d) Calcium Carbonate",
"Which of the gas is not known as green house gas? \n a) Methane \n b) Nitrous Oxide \n c) Carbon dioxide \n d) Hydrogen",
"Bromine is a \n a) Black Solid \n b) Red Liquid \n c) Colourless gas \n d) highly inflammable gas",
"The hardest substance available on earth is \n a) Gold \n b) Iron \n c) Diamond \n d) Platinum"
};

   String[] Answers = {"c", "d", "a", "d", "c","d", "b", "b", "a", "a"};
   String[] Answers2 = {"b", "b", "b", "a", "b", "a", "a", "d", "b", "c"};

   String[] Hints = {"The answer is also a programming language",
"They are the biggest phone producers on the list",
"It's the same reason a wall in the real world is used for",
"It's the one from the list you cannot use for anything else.",
"It's between 64 bits and 256 bits.",
"It's the least popular one from the list.",
"It is also a phone model google released",
"Creep...",
"It's the one that is typed differently",
"It's not 8, 9 , or 10"};

String[] Hints2 = {
    "It containg Hydrogen",
    "It is the only non metal that is liquid at an ordinary temp",
    "It's symbol is Mg",
    "It is somthing that is really hard to melt",
    "It is poisonous to humans and is used in thermometers as a liquid",
    "It is the element that has the largest proportion in the air we breath in",
    "It is Na2Co3",
    "It is a very light gas",
    "It is the colour of blood",
    "Diamond are forever"
};


   public ClientHandler(Socket server, DataInputStream data_input_stream, DataOutputStream data_output_stream){
       this.server = server;
       this.data_input_stream = data_input_stream;
       this.data_output_stream = data_output_stream;
   }

   @Override
   public void run(){
       String received;
       String toreturn;
        int score = 0;
        
        try {
            data_output_stream.write(Questions.length);
            data_output_stream.writeUTF("Select a category to play \n a) General Science \n b) Technology");
            String option = data_input_stream.readUTF();
       
            for (int i = 0; i <= Questions2.length - 1; i++) {
            if(option.equalsIgnoreCase("a")){
                    data_output_stream.writeUTF(Questions2[i]);
                  String answer = data_input_stream.readUTF();
                  if(answer.equalsIgnoreCase("Exit")){
                       System.out.println("Client " + this.server + " sends exit...");
                       System.out.println("Closing this connection.");
                       this.server.close();
                       System.out.println("Connection closed");
                       break;
                    }

                    if(answer.equalsIgnoreCase("Hint")){
                        data_output_stream.writeUTF(Hints2[i]);
                        answer = data_input_stream.readUTF();
                        if (answer.equalsIgnoreCase(Answers2[i])) {
                            data_output_stream.writeUTF((char)27 + "[32mThat is the correct answer" + (char)27 + "[39m\n");
                            score++;
                        }
                        else{data_output_stream.writeUTF((char)27 + "[31mThat is false. The answer is " + Answers[i] + (char)27 + "[39m\n");}
                    }
                    else {
                         if (answer.equalsIgnoreCase(Answers2[i])) {
                            data_output_stream.writeUTF((char)27 + "[32mThat is the correct answer" + (char)27 + "[39m\n");
                            score++;
                          }
                          else{data_output_stream.writeUTF((char)27 + "[31mThat is false. The answer is " + Answers[i] + (char)27 + "[39m\n");}
                        }

                    }

                
                else if (option.equalsIgnoreCase("b")) {
                  data_output_stream.writeUTF(Questions[i]);
                  String answer = data_input_stream.readUTF();

                  if(answer.equalsIgnoreCase("Exit")){
                       System.out.println("Client " + this.server + " sends exit...");
                       System.out.println("Closing this connection.");
                       this.server.close();
                       System.out.println("Connection closed");
                       break;
                    }
                    if(answer.equalsIgnoreCase("Hint")){
                        data_output_stream.writeUTF(Hints[i]);
                        answer = data_input_stream.readUTF();
                        if (answer.equalsIgnoreCase(Answers[i])) {
                            data_output_stream.writeUTF((char)27 + "[32mThat is the correct answer" + (char)27 + "[39m\n");
                            score++;
                        }
                        else{data_output_stream.writeUTF((char)27 + "[31mThat is false. The answer is " + Answers[i] + (char)27 + "[39m\n");}
                    }
                    else {
                         if (answer.equalsIgnoreCase(Answers[i])) {
                            data_output_stream.writeUTF((char)27 + "[32mThat is the correct answer" + (char)27 + "[39m\n");
                            score++;
                          }
                          else{data_output_stream.writeUTF((char)27 + "[31mThat is false. The answer is " + Answers[i] + (char)27 + "[39m\n");}
                        }
                }
                  
                }

                
             
           } catch (IOException e) {
               e.printStackTrace();
           }

       try
       {
        // Write final score and close resources
            data_output_stream.write(score);
           this.data_input_stream.close();
           this.data_output_stream.close();
            System.out.println("Closing connection " + this.server);
            this.server.close();
            System.out.println("Connection closed");

       }catch(IOException e){
           e.printStackTrace();
       }
   }
}