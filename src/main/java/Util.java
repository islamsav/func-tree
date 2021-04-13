import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Util {

    public static String pathBuilder(String... path) {
        StringBuilder sb = new StringBuilder();
        for (String s : path) {
            sb.append(s).append("/");
//            sb.append(s).append(File.separator);
        }
        return new String(sb);
    }

    public static void createTxtFile(List<String> list) {
        File fileTxt = new File(Util.pathBuilder("target") + "marmaid_map.txt");
        try (FileWriter writer = new FileWriter(fileTxt, false)) {
            for (String tag : list) {
                writer.write(tag + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
