package by.lms.libraryms.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class ReflectionManager {

    public <T> Map<String, String> getNotNullFieldsValues(T instance) {
        Map<String, String> fieldsMap = new HashMap<>();

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object fieldVal = field.get(instance);
                if (Objects.nonNull(fieldVal)) {
                    fieldsMap.put(fieldName, fieldVal.toString());
                }
            } catch (IllegalAccessException e) {
                //TODO добавить лог
                throw new RuntimeException(e);
            }
        }

        return fieldsMap;
    }
}
