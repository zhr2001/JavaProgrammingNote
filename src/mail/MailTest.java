package mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args)
            throws IOException, MessagingException
    {
        Properties props = new Properties();
        InputStream in = Files.newInputStream(Paths.get("E:\\code\\java\\JavaProgramming\\XMLParse\\src\\mail\\mail.properities"));
        props.load(in);
        List<String> lines = Files.readAllLines(Paths.get(args[0]), Charset.forName("UTF-8"));

        String from = lines.get(0);
        String to = lines.get(1);
        String subject = lines.get(2);

        StringBuilder builder = new StringBuilder();
        for (int i = 3; i < lines.size(); i++)
        {
            builder.append(lines.get(i));
            builder.append("\n");
        }

        Console console = System.console();
        String password = new String(console.readPassword("Password: "));

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, String.valueOf(new InternetAddress(to)));
        message.setSubject(subject);
        message.setText(builder.toString());
        Transport tr = mailSession.getTransport();
        tr.connect(null, password);
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }
}
