package by.lms.libraryms.domain;

import org.bson.types.ObjectId;

public interface Inventory {
    void setInventoryNumberId(ObjectId id);
    ObjectId getInventoryNumberId();
}
