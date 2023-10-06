import java.io.File;

public class PathManager {

    private static final String resourcePath = "src/main/resources";

    public String createAbsoluteResourceFilePath (String fileName) {
        File file = new File(resourcePath);
        String absoluteResourcePath = file.getAbsolutePath();
        return absoluteResourcePath + "\\" + fileName;
    }
}
