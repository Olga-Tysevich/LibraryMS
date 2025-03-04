package by.lms.libraryms.dto.resp;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListForPageDTO<T> {
    @NotNull(message = Constants.EMPTY_OBJECT_CLASS_MESSAGE)
    private String objectsClass;
    @NotNull(message = Constants.OBJECT_LIST_IS_NULL_MESSAGE)
    private List<T> list;
    private int totalPages;
    private int totalElements;
    private int nextPageIndex;
    private int previousPageIndex;
}
