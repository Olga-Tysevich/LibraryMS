package by.lms.libraryms.services.messages;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String subject;
    private String to;
    private String text;
}
