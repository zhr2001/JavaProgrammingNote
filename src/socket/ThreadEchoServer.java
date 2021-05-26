package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadEchoServer {
    public static void main(String[] args) {
        try
        {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            while (true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning "+i);
                Runnable r = new ThreadEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadEchoHandler implements Runnable
{
    private Socket incoming;

    public ThreadEchoHandler(Socket i)
    {
        incoming = i;
    }

    public void run()
    {
        try
        {
            try
            {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStrean = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStrean, true);

                out.println("Hello! Enter BYE to exit");

                boolean done = false;
                while(!done && in.hasNextLine())
                {
                    String line = in.nextLine();
                    out.println("Echo: "+line);
                    if (line.trim().equals("BYE"))
                    {
                        done = true;
                    }
                }
            }
            finally {
                incoming.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
