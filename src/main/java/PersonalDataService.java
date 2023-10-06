import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PersonalDataService {

    public CalculatedPersonalDataDTO getCalulatedPersonalData(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return readAndCalculateLines(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private CalculatedPersonalDataDTO readAndCalculateLines(BufferedReader reader) throws IOException {
        CalculatedPersonalDataDTO dto = new CalculatedPersonalDataDTO();
        String line = reader.readLine();
        while (line != null) {
            dto.calculateLine(line);
            line = reader.readLine();
        }
        return dto;
    }
}
