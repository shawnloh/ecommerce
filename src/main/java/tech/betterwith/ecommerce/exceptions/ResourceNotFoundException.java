package tech.betterwith.ecommerce.exceptions;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String field;
    UUID fieldId;

    public ResourceNotFoundException(String resourceName, String fieldName, String field, UUID fieldId) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, field));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, UUID fieldId) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }
}
