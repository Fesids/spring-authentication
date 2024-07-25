package com.application.app.constraints.validators;

import com.application.app.constraints.Exists;
import com.application.app.utils.Helpers;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

@Component
public class ExistsValidator implements ConstraintValidator<Exists, Object> {

    private String propertyName;

    private String repositoryName;

    private final ApplicationContext applicationContext;

    public ExistsValidator(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(Exists constraintAnnotation) {
        propertyName = constraintAnnotation.property();
        repositoryName = constraintAnnotation.repository();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object result;
        String finalRepositoryName = "com.application.app.repositories."+repositoryName;

        try{

            Class<?> type = Class.forName(finalRepositoryName);
            Object instance = this.applicationContext.getBean(finalRepositoryName);

            final Object propertyObj = BeanUtils.getProperty(value, propertyName);

            String finalPropertyName = Helpers.capitalize(propertyName);

            result = type.getMethod("findBy"+finalPropertyName, String.class).invoke(instance, propertyObj.toString());



        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();

            return false;

        }

        return result != null;
    }
}
