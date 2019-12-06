package com.voronkov.cafevoiter.validator;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.repository.CrudCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CafeValidator implements Validator {

    @Autowired
    private CrudCafeRepository cafeRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return Cafe.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Cafe cafe = (Cafe) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (cafe.getName().length() < 2 || cafe.getName().length() > 50) {
            errors.rejectValue("name", "Неправильное количество символов");
        }
    }
}
