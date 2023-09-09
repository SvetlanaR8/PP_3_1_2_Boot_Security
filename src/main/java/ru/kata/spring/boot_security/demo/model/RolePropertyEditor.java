package ru.kata.spring.boot_security.demo.model;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class RolePropertyEditor extends PropertyEditorSupport {


    @Override
    public void setAsText(String text) {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            Role role = new Role();
            role.setName(text);
            setValue(role);
        }
    }
}
