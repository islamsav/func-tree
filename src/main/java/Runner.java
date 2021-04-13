import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.create();
    }
}