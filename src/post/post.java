package post;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class post {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        InputStream in = Files.newInputStream(Paths.get("E:\\code\\java\\JavaProgramming\\XMLParse\\src\\post\\.properties"));
        props.load(in);
        String url = props.remove("url").toString();
        String result = doPost(url, props);
        System.out.println(result);
    }

    public static String doPost(String urlString, Map<Object, Object> nameValuePairs)
            throws IOException
    {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        PrintWriter out = new PrintWriter(connection.getOutputStream());
        boolean first = true;
        for (Map.Entry<Object, Object> pair : nameValuePairs.entrySet())
        {
            if (first) first = false;
            else out.print('&');

            String name = pair.getKey().toString();
            String value = pair.getValue().toString();
            out.print(name);
            out.print('=');
            out.print(URLEncoder.encode(value, "UTF-8"));
        }

        StringBuilder response = new StringBuilder();
        try (Scanner in = new Scanner(connection.getInputStream()))
        {
            while (in.hasNextLine())
            {
                response.append(in.nextLine());
                response.append("\n");
            }
        }
        catch (IOException e)
        {
            if (!(connection instanceof HttpURLConnection)) throw e;
            InputStream err = ((HttpURLConnection) connection).getErrorStream();
            if (err == null) throw e;
            Scanner in =new Scanner(err);
            response.append(in.nextLine());
            response.append("\n");
        }

        return response.toString();
    }
}
