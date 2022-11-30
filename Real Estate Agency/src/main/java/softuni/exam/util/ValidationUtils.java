package softuni.exam.util;

import org.springframework.stereotype.Component;

@Component
public interface ValidationUtils {
    <E> boolean isValid(E entity);
}
