import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    private String projectName = "project_name";
    private List<String> list = new ArrayList<>();


    private final Configuration CONFIG = ConfigFactory.create(
            Configuration.class,
            System.getProperties());

    private final String FEATURES_PATH = CONFIG.path().replace(projectName, CONFIG.project());
    private final Path PARENT = Paths.get(FEATURES_PATH);

    public void create() throws IOException {
        list.add(CONFIG.route());
        list.add(PARENT.getFileName().toString() + "{" + CONFIG.project().toUpperCase() + "}");
        Files.walkFileTree(PARENT, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                Path parentName = dir.getParent().getFileName();
                Path currentName = dir.getFileName();
                if (parentName != null && !parentName.getFileName().toString().equals("resources")) {
                    list.add(format1(parentName.toString(), currentName.toString(), ArrowFormat.DEFAULT));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        Util.createTxtFile(list);
    }

    /**
     * Рисует квадратные ноды
     */
    private String format1(String parent, String current, ArrowFormat format) {
        return String.format("%s %s %s", parent, format.getFormat(), current);
    }

    /**
     * Рисует закругленные ноды
     */
    private String format2(String parent, String current, ArrowFormat format) {
        return String.format("%s([%s]) %s %s([%s])", parent, parent, format.getFormat(), current, current);
    }
}

