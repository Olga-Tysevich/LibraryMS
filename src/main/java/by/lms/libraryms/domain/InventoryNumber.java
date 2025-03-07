package by.lms.libraryms.domain;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "inventory_numbers")
@CompoundIndexes(
        @CompoundIndex(name = "prefix_numbers_index", def = "{'prefix': 1, 'numbers': 1}", unique = true)
)
public final class InventoryNumber extends AbstractDomainClass {
    @NotNull
    private final InventoryPrefixEnum prefix;
    @NotNull
    private final Character delimiter;
    @Getter
    @NotNull
    @Size(min = 1, message = Constants.EMPTY_INVENTORY_NUMBER_MESSAGE)
    private final List<InventoryNumberElement> numbers;
    @Setter
    @Getter
    private Boolean isDisposedOf = false;
    @Setter
    @Getter
    private LocalDate disposedDate;

    @Version
    private int version;

    public InventoryNumber(InventoryPrefixEnum prefix, InventoryNumberElement lastNumber, InventoryNumberElement ... numbers) {
        this.prefix = prefix;
        this.delimiter = prefix.getDelimiter();
        this.numbers = new ArrayList<>(numbers.length + 1);
        this.numbers.addAll(Arrays.asList(numbers));
        this.numbers.addLast(lastNumber);
    }

    public String number() {
        return prefix.name() + delimiter + numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(delimiter.toString()));
    }
}
