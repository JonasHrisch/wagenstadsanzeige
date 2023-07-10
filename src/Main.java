import java.util.Map;

public class Main {

    private static final String FILEPATH = "C:/Users/jh/DB/Wagenreihungsplan_RawData_201712112";

    public static void main(String[] args) {

        String input = "GET/station/FF/train/2310/waggon/8";

        Map<String, String> szenario = utils.parseInput(input);

        String filename = utils.findFilenameByPrefix(FILEPATH,szenario.get("station"));

        System.out.println("Filename: " + filename);

        System.out.println("Operation: " + szenario.get("operation"));
        System.out.println("Station: " + szenario.get("station"));
        System.out.println("Train: " + szenario.get("train"));
        System.out.println("Waggon: " + szenario.get("wagon"));

        if (szenario.get("operation").equals("GET")){
            train train = new train(filename, Integer.parseInt(szenario.get("train")), Integer.parseInt(szenario.get("wagon")));
            Map<String, Object> sections = train.getSections();

            System.out.println("Sections: " + sections);
        }
    }


}
