import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static PathManager pathManager = new PathManager();
    private static PersonalDataService personalDataService = new PersonalDataService();
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        List<String> fileNames = Arrays.asList("data1.txt", "data2.txt", "data3.txt", "data4.txt", "data5.txt", "data6.txt", "data7.txt", "data8.txt", "data9.txt", "data10.txt", "data11.txt", "data12.txt");

        CalculatedPersonalDataDTO dto = multiThread(fileNames);

        long end = System.currentTimeMillis();
        long latency = end - start;

        System.out.println("Latency: " + latency);
        System.out.println(dto);


    }

    //~1430 ms
    public static CalculatedPersonalDataDTO singleThread(List<String> fileNames) {
        CalculatedPersonalDataDTO dto = new CalculatedPersonalDataDTO();
        for(String fileName : fileNames) {
            dto.add(personalDataService.getCalulatedPersonalData(pathManager.createAbsoluteResourceFilePath(fileName)));
        }
        return dto;
    }

    // 2 wątki ~860 ms
    // 3 wątki ~670 ms
    // 4 wątki ~600 ms
    public static CalculatedPersonalDataDTO multiThread(List<String> fileNames) {
        CalculatedPersonalDataDTO dto = new CalculatedPersonalDataDTO();
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<CalculatedPersonalDataDTO> result1 = service.submit(() -> singleThread(fileNames.subList(0, 3)));
        Future<CalculatedPersonalDataDTO> result2 = service.submit(() -> singleThread(fileNames.subList(3, 6)));
        Future<CalculatedPersonalDataDTO> result3 = service.submit(() -> singleThread(fileNames.subList(6, 9)));
        Future<CalculatedPersonalDataDTO> result4 = service.submit(() -> singleThread(fileNames.subList(9, 12)));

        try {
            dto.add(result1.get());
            dto.add(result2.get());
            dto.add(result3.get());
            dto.add(result4.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return dto;

    }

}