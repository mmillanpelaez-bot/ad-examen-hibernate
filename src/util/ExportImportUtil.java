package util;

import model.Autor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExportImportUtil {

    // 1. Leer líneas de fichero de texto ("datos")
    public static List<String> readFicheiro(String rutaFicheiro) {
        List<String> lista = new ArrayList<>();
        File file = new File(rutaFicheiro);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        lista.add(linea.trim());
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro lendo o ficheiro: " + e.getMessage());
            }
        } else {
            System.out.println("Non existe o ficheiro: " + rutaFicheiro);
        }
        return lista;
    }

    // 2. Serializar objeto
    public static void serializar(Object obj, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Erro serializando: " + e.getMessage());
        }
    }

    // 3. Deserializar objeto (probable extensión)
    public static Object deserializar(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro deserializando: " + e.getMessage());
            return null;
        }
    }

    // 4. Exportar Autor a XML con StAX (estilo Help_UwU_)
    public static void autorToXML(Autor autor, String ruta) {
        if (autor == null) return;
        try (FileWriter fw = new FileWriter(ruta)) {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xsw = xof.createXMLStreamWriter(fw);

            xsw.writeStartDocument("1.0");
            xsw.writeStartElement("autores");
                xsw.writeStartElement("autor");
                xsw.writeAttribute("id", String.valueOf(autor.getId()));

                    xsw.writeStartElement("nome");
                    xsw.writeCharacters(autor.getNome());
                    xsw.writeEndElement();

                    xsw.writeStartElement("apelidos");
                    xsw.writeCharacters(autor.getApelidos());
                    xsw.writeEndElement();

                    xsw.writeStartElement("nacionalidade");
                    xsw.writeCharacters(autor.getNacionalidade());
                    xsw.writeEndElement();

                xsw.writeEndElement();
            xsw.writeEndElement();
            xsw.writeEndDocument();
            xsw.flush();
            xsw.close();
        } catch (Exception e) {
            System.out.println("Erro exportando XML: " + e.getMessage());
        }
    }

    // 5. Leer Autor desde XML con DOM (probable extensión)
    public static Autor xmlToAutor(String ruta) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(ruta));
            Element e = (Element) doc.getElementsByTagName("autor").item(0);
            Autor a = new Autor();
            a.setId(Integer.parseInt(e.getAttribute("id")));
            a.setNome(e.getElementsByTagName("nome").item(0).getTextContent());
            a.setApelidos(e.getElementsByTagName("apelidos").item(0).getTextContent());
            a.setNacionalidade(e.getElementsByTagName("nacionalidade").item(0).getTextContent());
            return a;
        } catch (Exception e) {
            System.out.println("Erro lendo XML: " + e.getMessage());
            return null;
        }
    }
}