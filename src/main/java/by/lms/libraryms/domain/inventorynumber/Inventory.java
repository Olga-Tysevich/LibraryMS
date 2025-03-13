package by.lms.libraryms.domain.inventorynumber;

import org.bson.types.ObjectId;

public interface Inventory {
    String getId();
    void setInventoryNumberId(ObjectId id);
    ObjectId getInventoryNumberId();
}
