import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path parentName = dir.getParent().getFileName();
                Path currentName = dir.getFileName();
                if (parentName != null && !parentName.getFileName().toString().equals("resources")) {
                    long featureCount = scenariosCount(dir.toAbsolutePath());
                    String text = String.format("%s(%s - %s)", currentName, currentName, featureCount);
                    list.add(format1(parentName.toString(), text, ArrowFormat.DEFAULT));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        Util.createTxtFile(list);
    }

    private long scenariosCount(Path path) throws IOException {
        final int[] count = {0};
        List<Path> files = this.findFeatureFiles(path);
        for (Path file : files) {
            Scanner scanner = new Scanner(file.toFile());
            while (scanner.hasNextLine()) {
                String line = scanner.useDelimiter("\\n").nextLine().trim();
                if (line.contains("Сценарий:") || line.contains("Структура сценария")) {
                    count[0]++;
                }
            }
        }
        return count[0];
    }

    private List<Path> findFeatureFiles(Path path) throws IOException {
        return Files.walk(path)
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().endsWith(".feature"))
                .collect(Collectors.toList());
    }

    /**
     * Рисует квадратные ноды
     */
    private String format1(String parent, String current, ArrowFormat format) {
        return String.format("%s %s %s", parent, format.getFormat(), current);
    }
}

