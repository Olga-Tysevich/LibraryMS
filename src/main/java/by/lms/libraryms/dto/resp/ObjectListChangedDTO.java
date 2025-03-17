package by.lms.libraryms.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ObjectListChangedDTO<T> {
    private List<ObjectChangedDTO<T>> objects;
}
