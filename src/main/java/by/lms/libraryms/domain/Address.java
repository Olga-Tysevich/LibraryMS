package by.lms.libraryms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true)
@Data
@Document(collection = "addresses")
public class Address extends AbstractDomainClass {
    private String city;
    private String street;
    private String houseNumber;
    private int apartmentNumber;
    private String postalCode;
    private Set<ObjectId> userIds;
}
