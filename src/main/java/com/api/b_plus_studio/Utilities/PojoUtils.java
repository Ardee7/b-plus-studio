package com.api.b_plus_studio.Utilities;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class PojoUtils {
    /**
     * Utility method to copy non-null properties from a source object to a target object.
     * This method uses reflection to iterate through the fields of both the source and target objects.
     * For each field in the source object that is non-null, it attempts to copy the value
     * to the corresponding field in the target object if their names and types match.
     *
     * Special handling is implemented for fields of type {@code byte[]} and {@code String},
     * allowing type conversions between these types when required. (Mostly done with updating
     * investment nickname, so check update investment nickname when doing improvement, or refactor).
     *
     * @param <S> the type of the source object
     * @param <T> the type of the target object
     * @param source the source object from which properties are copied
     * @param target the target object to which properties are copied
     * @throws RuntimeException if an error occurs while accessing or setting a field
     *
     * <p><b>Example Usage:</b></p>
     * <pre>
     * {@code
     * SourceObject source = new SourceObject();
     * source.setName("John");
     * source.setAge(30);
     *
     * TargetObject target = new TargetObject();
     * target.setName("Doe");
     *
     * UpdateUtil.copyNonNullProperties(source, target);
     *
     * // After execution, target.getName() will be "John" and target.getAge() will be 30.
     * }
     * </pre>
     */
    public static <S, T> void copyNonNullProperties(S source, T target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();

        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true); // Allow access to private fields
            try {
                Object value = sourceField.get(source);
                if (value != null) {
                    // Find corresponding field in the target
                    for (Field targetField : targetFields) {
                        if (targetField.getName().equals(sourceField.getName())) {
                            targetField.setAccessible(true);

                            // Special case: handle byte[] type explicitly
                            if (targetField.getType().equals(byte[].class) && sourceField.getType().equals(byte[].class)) {
                                targetField.set(target, value);
                            } else if (targetField.getType().equals(String.class) && value instanceof byte[]) {
                                // Convert byte[] to String
                                targetField.set(target, new String((byte[]) value, StandardCharsets.UTF_8));
                            } else if (targetField.getType().equals(byte[].class) && value instanceof String) {
                                // Convert String to byte[]
                                targetField.set(target, ((String) value).getBytes(StandardCharsets.UTF_8));
                            } else {
                                // General case: directly copy the value
                                targetField.set(target, value);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field: " + sourceField.getName(), e);
            }
        }
    }
}
