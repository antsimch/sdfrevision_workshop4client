package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class ClientMain 
{
    public static void main( String[] args )
    {
        String host = "localhost";
        int port = 12345;
        
        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            System.out.println("args[0]: host, args[1]: port");
            System.exit(1);
        }

        try {
            Socket socket = new Socket(host, port);
            System.out.println("Client connected to server on port " + port);

            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Scanner scan = new Scanner(System.in);
            String clientOutgoing = "";

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            String clientIncoming = "";
            
            while (!clientOutgoing.equalsIgnoreCase("close")) {
                System.out.print(">");
                clientOutgoing = scan.nextLine();
                dos.writeUTF(clientOutgoing);
                dos.flush();

                if (clientOutgoing.equalsIgnoreCase("get-cookie")) {
                    clientIncoming = dis.readUTF();
                    System.out.println(clientIncoming.replace("cookie-text ",""));
                }
            }

            dos.close();
            bos.close();
            os.close();
            scan.close();
            dos.close();
            bos.close();
            os.close();
            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
