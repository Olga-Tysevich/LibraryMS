package by.lms.libraryms.facades;

import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.req.AuthorDTO;
import jakarta.validation.constraints.NotNull;

public interface AuthorFacade {
    ObjectChangedDTO addAuthor(@NotNull AuthorDTO authorDTO);

    ObjectChangedDTO updateAuthor(@NotNull AuthorDTO authorDTO);

    ObjectChangedDTO deleteAuthor(@NotNull AuthorSearchReqDTO authorReq);

    AuthorDTO getAuthor(@NotNull AuthorSearchReqDTO authorReq);

    ListForPageDTO<AuthorDTO> getAuthors(@NotNull AuthorSearchReqDTO searchReqDTO);
}
