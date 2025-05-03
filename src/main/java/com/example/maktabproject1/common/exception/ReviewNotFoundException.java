import com.example.maktabproject1.common.ErrorMessage;
import com.example.maktabproject1.common.exception.NotFoundException;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException() {
        super(ErrorMessage.REVIEW_NOT_FOUND.getMessage());
    }
}
