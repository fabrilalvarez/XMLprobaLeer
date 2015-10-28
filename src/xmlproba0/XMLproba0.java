package xmlproba0;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Fran
 */
public class XMLproba0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String nombreArchivo = "./autores.xml";
        //escribir(nombreArchivo);
        //leerSinEtiquetas(nombreArchivo);
        leerConEtiquetas(nombreArchivo);
    }

    public static void escribir(String nombreArchivo) throws Exception {
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        XMLStreamWriter xtw = xof.createXMLStreamWriter(new FileWriter(nombreArchivo));
        xtw.writeStartDocument("utf-8", "1.0");//escribe a declaracion XML con a Version especificada
        xtw.writeStartElement("autores");//escribe o tag de inicio de un elemento
        xtw.writeStartElement("autor");
        xtw.writeAttribute("codigo", "a1");
        xtw.writeStartElement("nome");
        xtw.writeCharacters("Alexandre Dumas");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeStartElement("titulo");
        xtw.writeCharacters("El conde de montecristo");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeStartElement("titulo");
        xtw.writeCharacters("Los miserables");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeEndElement();
        xtw.writeStartElement("autor");
        xtw.writeAttribute("codigo", "a2");
        xtw.writeStartElement("nome");
        xtw.writeCharacters("Fiodor Dostoyevski");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeStartElement("titulo");
        xtw.writeCharacters("El idiota");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeStartElement("titulo");
        xtw.writeCharacters("Noches blancas");//escribe o contido do elemento 
        xtw.writeEndElement();
        xtw.writeEndElement();
        xtw.writeEndElement();//escribe o tag de peche do elemento actual
        xtw.writeEndDocument();
        xtw.flush();
        xtw.close();
        System.out.println("Archivo Escrito");
    }

    public static void leerSinEtiquetas(String nombreArchivo) throws Exception {
        System.out.println("Lectura del archivo modo 1");
        try {
            DocumentBuilderFactory fábricaCreadorDocumento = DocumentBuilderFactory.newInstance();
            DocumentBuilder creadorDocumento = fábricaCreadorDocumento.newDocumentBuilder();
            Document documento = creadorDocumento.parse(nombreArchivo);
            //Obtener el elemento raíz del documento
            Element raiz = documento.getDocumentElement();
            //Obtener la lista de nodos que tienen etiqueta "autor"
            NodeList listaAutores = raiz.getElementsByTagName("autor");
            //Recorrer la lista de autores
            for (int i = 0; i < listaAutores.getLength(); i++) {
                //Obtener de la lista un empleado tras otro
                Node autor = listaAutores.item(i);
                System.out.println("Autor " + i);
                System.out.println("==========");
                //Obtener la lista de los datos que contiene ese autor
                NodeList datosAutor = autor.getChildNodes();
                //Recorrer la lista de los datos que contiene el autor
                for (int j = 0; j < datosAutor.getLength(); j++) {
                    //Obtener de la lista de datos un dato tras otro
                    Node dato = datosAutor.item(j);
                    //Comprobar que el dato se trata de un nodo de tipo Element
                    if (dato.getNodeType() == Node.ELEMENT_NODE) {
                        //Mostrar el nombre del tipo de dato
                        System.out.print(dato.getNodeName() + ": ");
                        //El valor está contenido en un hijo del nodo Element
                        Node datoContenido = dato.getFirstChild();
                        //Mostrar el valor contenido en el nodo que debe ser de tipo Text
                        if (datoContenido != null && datoContenido.getNodeType() == Node.TEXT_NODE) {
                            System.out.println(datoContenido.getNodeValue());
                        }
                    }
                }
                //Se deja un salto de línea de separación entre cada empleado
                System.out.println();
            }
        } catch (SAXException ex) {
            System.out.println("ERROR: El formato XML del fichero no es correcto\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("ERROR: Se ha producido un error el leer el fichero\n" + ex.getMessage());
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            System.out.println("ERROR: No se ha podido crear el generador de documentos XML\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void leerConEtiquetas(String nombreArchivo) throws Exception {
        System.out.println("Lectura del archivo modo 2");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream fis = new FileInputStream(nombreArchivo);
        XMLStreamReader reader = factory.createXMLStreamReader(fis);
        // FLUJO DE LECTURA
        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                if (reader.getAttributeLocalName(0) != null) {
                    System.out.print("<" + reader.getLocalName() + " " + reader.getAttributeLocalName(0) + "='" + reader.getAttributeValue(0) + "'>");
                } else {
                    System.out.print("<" + reader.getLocalName() + ">");
                }
            } else if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                System.out.print(reader.getText());
            } else if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {
                System.out.print("</" + reader.getLocalName() + ">");
            }
        }
        System.out.println("");
        reader.close();
    }
}
