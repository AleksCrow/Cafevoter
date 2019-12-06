package com.voronkov.cafevoiter.validator;

import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (user.getUsername().length() < 2 || user.getUsername().length() > 50) {
            errors.rejectValue("name", "Неправильное количество символов");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Пользователь с таким email уже зарегистрирован");
        }
    }
}
