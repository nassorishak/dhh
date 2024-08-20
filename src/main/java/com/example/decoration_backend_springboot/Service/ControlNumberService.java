import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class ControlNumberService {

    public String generateControlNumber() {
        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = LocalDate.now().format(dateFormatter);

        // Generate a random 3-digit number
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // Between 100 and 999

        // Construct the control number
        return String.format("IS-HAK-%s%03d", dateStr, randomNumber);
    }
}
