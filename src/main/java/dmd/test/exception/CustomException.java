package dmd.test.exception;

import java.text.MessageFormat;
import java.util.Optional;

import dmd.test.config.PropertiessConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomException {
    private static PropertiessConfig pc;

    @Autowired
    public CustomException(PropertiessConfig pc) {
        CustomException.pc = pc;
    }

    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
        String template = getMsgTemplate(entityType, exceptionType);
        return throwException(exceptionType, template, args);
    }

    private static RuntimeException throwException(ExceptionType exceptionType, String msg, String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(msg, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new DuplicateEntityException(format(msg, args));
        }
        return new RuntimeException(format(msg, args));
    }

    private static String getMsgTemplate(EntityType entityType, ExceptionType exceptionType) {
        return entityType.name().toLowerCase().concat(".").concat(exceptionType.getValue().toLowerCase());
    }

    private static String format(String template, String... args) {
        Optional<String> templateContent = Optional.ofNullable(pc.getValue(template));
        if (templateContent.isPresent()) {
            return MessageFormat.format(templateContent.get(), (Object[]) args);
        }
        return String.format(template, (Object[]) args);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }
}
