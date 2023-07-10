import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class utils {

    public static Map<String, String> parseInput(String input) {
        Pattern pattern = Pattern.compile("^(\\w+)/station/(\\w+)/train/(\\d+)/waggon/(\\d+)$");
        Matcher matcher = pattern.matcher(input);

        Map<String, String> result = new HashMap<>();

        if (matcher.find()) {
            result.put("operation", matcher.group(1));
            result.put("station", matcher.group(2));
            result.put("train", matcher.group(3));
            result.put("wagon", matcher.group(4));
        }

        return result;
    }

    public static String findFilenameByPrefix(String folderPath, String prefix) {
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();


            for (File file : files) {
                String fileName = file.getName();

                if (fileName.startsWith(prefix + "_")) {
                    return fileName;
                }

            }
        }

        return null;
    }
}
