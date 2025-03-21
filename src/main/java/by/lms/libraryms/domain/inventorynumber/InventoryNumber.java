package by.lms.libraryms.domain.inventorynumber;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "inventory_numbers")
@CompoundIndexes(
        @CompoundIndex(name = "prefix_numbers_index", def = "{'prefix': 1, 'numbers': 1}", unique = true)
)
public final class InventoryNumber extends AbstractDomainClass {
    @NotNull
    @Setter(AccessLevel.PACKAGE)
    @Getter
    private InventoryPrefixEnum prefix;
    @NotNull
    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PACKAGE)
    private Character delimiter;
    @NotNull
    @Setter(AccessLevel.PACKAGE)
    @Getter
    @Size(min = 1, message = Constants.EMPTY_INVENTORY_NUMBER_MESSAGE)
    private List<InventoryNumberElement> numbers;
    private Boolean isDisposedOf = false;
    private LocalDate disposedDate;
    private boolean isRelated = false;
    private ObjectId relatedId;

    @Version
    private int version;

    public InventoryNumber(@NotNull InventoryPrefixEnum prefix, @NotEmpty List<InventoryNumberElement> numbers) {
        this.prefix = prefix;
        this.delimiter = prefix.getDelimiter();
        this.numbers = numbers;
    }

    public String number() {
        return prefix.name() + delimiter + numbers.stream()
                .map(InventoryNumberElement::number)
                .map(String::valueOf)
                .collect(Collectors.joining(delimiter.toString()));
    }

    public void setRelatedId(@NotNull ObjectId relatedId) {
        if (Objects.nonNull(this.relatedId)) {
            throw new IllegalArgumentException("Attempt to set a new inventory number for an object: " + this + "!ObjectId: " + relatedId);
        }
        this.relatedId = relatedId;
    }
}
