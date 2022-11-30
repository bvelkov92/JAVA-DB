package com.example.football.util;

import org.springframework.stereotype.Component;

@Component
public interface ValidationUtils {
    <E> boolean isValid(E entity);
}
