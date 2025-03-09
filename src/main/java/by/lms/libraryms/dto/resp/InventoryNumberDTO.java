package by.lms.libraryms.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryNumberDTO {
    private String number;
    private boolean isDisposedOf;
    private LocalDate disposedDate;
}
