package by.lms.libraryms.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ParamsManager {

    public <T> String getParamsAsString(T instance) {
        Map<String, String> params = ReflectionManager.getNotNullFieldsValues(instance);
        List<String> keys = new ArrayList<>(params.keySet());

        StringBuilder result = new StringBuilder();
        for (String key : keys) {
            result.append("field: ").append(key).append(", val: ").append(params.get(key));
        }

        return result.toString();
    }
}
