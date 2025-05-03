import com.example.maktabproject1.common.ErrorMessage;
import com.example.maktabproject1.common.exception.NotFoundException;

public class SpecialistNotFoundException extends NotFoundException {
    public SpecialistNotFoundException() {
        super(ErrorMessage.SPECIALIST_NOT_FOUND.getMessage());
    }
}
