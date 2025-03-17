package by.lms.libraryms.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class ChangingObjectException extends RuntimeException {
    private List<Objects> changedObjects;
    private List<Objects> unchangedObjects;

    public ChangingObjectException(String message, List<Objects> changedObjects, List<Objects> unchangedObjects) {
        super(message);
        this.changedObjects = changedObjects;
        this.unchangedObjects = unchangedObjects;
    }

    public ChangingObjectException(String message) {
        super(message);
    }

    public ChangingObjectException() {
    }
}
