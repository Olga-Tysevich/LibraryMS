package by.lms.libraryms.services.searchobjects;

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
public class ListForPageResp<T> {
    @NotNull(message = "{validation.object.class.null}")
    private String objectsClass;
    @NotNull(message = "{validation.object.list.null}")
    private List<T> list;
    private int totalPages;
    private int totalElements;
    private int nextPageIndex;
    private int previousPageIndex;
}
