import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class train {
    private String fileName;
    private Integer train;
    private Integer wagon;

    public train(String fileName, Integer trainNumber, Integer wagonNumber) {
        this.fileName = fileName;
        this.train = trainNumber;
        this.wagon = wagonNumber;
    }

    public Map<String, Object> getSections() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("Wagenreihungsplan_RawData_201712112/" + fileName));

            // Unnecessary empty text nodes are removed, and adjacent text nodes are combined into a single text node
            doc.getDocumentElement().normalize();


            NodeList trainList = doc.getElementsByTagName("train");

            boolean trainNumberFound = false; // Track if the train number is found
            Map<String, Object> wagonSections = new HashMap<>();

            // Iterate through trains
            for (int idx_train = 0; idx_train < trainList.getLength(); idx_train++) {

                // Different types of nodes, such as element nodes, text nodes, attribute nodes
                Element trainElement = (Element) trainList.item(idx_train);

                NodeList trainNumberList = trainElement.getElementsByTagName("trainNumber");

                for (int i = 0; i < trainNumberList.getLength(); i++) {
                    String trainNumberStr = trainNumberList.item(i).getTextContent();

                    if (this.train == Integer.parseInt(trainNumberStr)) {
                        trainNumberFound = true;
                        break; // Exit the inner loop if the train number is found
                    }
                }

                if (trainNumberFound) {
                    NodeList wagonList = trainElement.getElementsByTagName("waggon");

                    // Iterate through the wagons
                    for (int j = 0; j < wagonList.getLength(); j++) {
                        Element wagonElement = (Element) wagonList.item(j);

                        String wagonNumber = wagonElement.getElementsByTagName("number").item(0).getTextContent();
                        if (!wagonNumber.isEmpty() && this.wagon == Integer.parseInt(wagonNumber)) {

                            // Save sections into a list
                            List<String> sections = new ArrayList<>();
                            NodeList sectionsList = wagonElement.getElementsByTagName("sections");

                            for (int k = 0; k < sectionsList.getLength(); k++) {
                                Element sectionElement = (Element) sectionsList.item(k);
                                String section = sectionElement.getElementsByTagName("identifier").item(0).getTextContent();
                                sections.add(section);
                            }

                            // Store sections in the wagon details dictionary
                            wagonSections.put("sections", sections);

                            break; // Exit the loop if the wagon number is found
                        }
                    }

                    break; // Exit the outer loop if the train number is found
                }
            }

            if (!trainNumberFound) {
                System.out.println("Train number " + this.train + " is not present.");
            }

            return wagonSections;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
