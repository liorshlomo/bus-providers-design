package pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArrivalsXmlParser implements IArrivalsParser {

    @Override
    public List<StopEta> parseResponse(String xml)
    {
        return parseResponseFromXml(xml);
    }
    public List<StopEta> parseResponseFromXml(String xml)
    {
        List<StopEta> stopEtas = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document document = builder.parse(inputStream);

            Element rootElement = document.getDocumentElement();
            NodeList arrivalNodes = rootElement.getElementsByTagName("Stop");

            for (int i = 0; i < arrivalNodes.getLength(); i++) {
                Node arrivalNode = arrivalNodes.item(i);
                if (arrivalNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element arrivalElement = (Element) arrivalNode;
                    String stopId = arrivalElement.getAttribute("ID");
                    String etaTime = arrivalElement.getTextContent().trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    Instant instant = LocalDateTime.parse(etaTime, formatter).atZone(ZoneId.systemDefault()).toInstant();
                    Date date = Date.from(instant);
                    StopEta stopEta = new StopEta(Integer.parseInt(stopId), date);
                    stopEtas.add(stopEta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stopEtas;

    }
}
