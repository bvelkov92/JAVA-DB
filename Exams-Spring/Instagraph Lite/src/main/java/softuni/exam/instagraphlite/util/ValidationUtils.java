package softuni.exam.instagraphlite.util;

import org.springframework.stereotype.Component;

@Component
public interface ValidationUtils {
    <E> boolean isValid(E entity);
}
