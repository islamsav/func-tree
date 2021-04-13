import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MapGenerator {
    private final String PROJECT_NAME = "project_name";

    private final Configuration configuration = ConfigFactory.create(
            Configuration.class,
            System.getProperties());

    private final String FEATURES_PATH = configuration.path().replace(PROJECT_NAME, configuration.project());
    private final Path PARENT = Paths.get(FEATURES_PATH);
    private List<String> list = new ArrayList<>();

    public void create() throws IOException {
        list.add("graph TD;");
        list.add(PARENT.getFileName().toString() + "{WEB}");
        Files.walkFileTree(PARENT, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                Path parentName = dir.getParent().getFileName();
                Path currentName = dir.getFileName();
                if (parentName != null && !parentName.getFileName().toString().equals("resources")) {
                    list.add(format1(parentName.toString(), currentName.toString()));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        Util.createTxtFile(list);
    }

    private String format1(String parent, String current) {
        return String.format("%s --> %s", parent, current);
    }

//    https://mermaid-js.github.io/mermaid-live-editor
//    https://mermaid-js.github.io/mermaid/
}

