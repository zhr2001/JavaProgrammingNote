import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class xmlFirst {
    static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    static DocumentBuilder builder;

    static {
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    static String dir = "src/test.xml";

    public static void parse() throws IOException, SAXException {
       File f = new File(dir);
       Document doc = builder.parse(f);
       Element root = doc.getDocumentElement();
       System.out.println(root);
       System.out.println(root.getTagName());
       System.out.println(root.getAttribute("name"));
       NodeList children = root.getChildNodes();
       for (int i = 0; i < children.getLength(); i++) {
           if (children.item(i) instanceof Element) {
               System.out.println();
               System.out.println(children.item(i));
               System.out.println();
           }
       }
    }

    public static void main(String[] args) throws IOException, SAXException {
        parse();
    }
}
