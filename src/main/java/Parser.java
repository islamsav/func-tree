import org.aeonbits.owner.ConfigFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private static final String PROJECT_NAME = "project_name";

    private static final Configuration configuration = ConfigFactory.create(
            Configuration.class,
            System.getProperties());

    private static final String FEATURES_PATH = configuration.path().replace(PROJECT_NAME, configuration.project());
    private static final Path PARENT = Paths.get(FEATURES_PATH);
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        list.add("graph TD;");
        list.add(PARENT.getFileName().toString() + "{WEB}");

        Files.walkFileTree(PARENT, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path parentName = dir.getParent().getFileName();
                Path currentName = dir.getFileName();
                if (parentName != null && !parentName.getFileName().toString().equals("resources")) {
                    list.add(painter1(parentName.toString(), currentName.toString(), null));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        Util.createTxtFile(list);
    }

    private static String painter1(String parent, String current, String between) {
        between = between == null ? "" : "|" + between + "|";
        return String.format("%s --> %s%s", parent, between, current);
    }
}

