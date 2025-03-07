package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.utils.Constants;
import by.lms.libraryms.utils.UniqueKeyGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO extends AbstractDTO {
    @NotEmpty(message = Constants.EMPTY_TITLE_MESSAGE)
    private String title;
    @NotNull(message = Constants.EMPTY_YEAR_MESSAGE)
    private int year;
    @NotNull
    @Size(min = 1, message = Constants.EMPTY_AUTHOR_MESSAGE)
    private Set<String> authorIds;
    @NotNull
    @Size(min = 1, message = Constants.EMPTY_GENRE_MESSAGE)
    private Set<String> genreIds;
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private String uniqueKey;

    public void setUniqueKey() {
        uniqueKey = UniqueKeyGenerator.generateUniqueKey(this);
    }

}
