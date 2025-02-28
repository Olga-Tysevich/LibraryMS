package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "inventory_numbers")
public final class InventoryNumber extends AbstractDomainClass {
    @NotNull
    private final Character prefix;
    @NotNull
    private final Character delimiter;
    private final List<Integer> numbers;
    @Setter
    @Getter
    private Boolean isDisposedOf = false;
    @Setter
    @Getter
    private LocalDate disposedDate;

    public InventoryNumber(Character prefix, Character delimiter, Integer firstNumber, Integer ... numbers) {
        this.prefix = prefix;
        this.delimiter = delimiter;
        this.numbers = new ArrayList<>(firstNumber);
        if (Objects.nonNull(numbers)) {
            this.numbers.addAll(List.of(numbers));
        }
    }
}
