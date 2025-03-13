package by.lms.libraryms.facades.impl;

import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.StockBookFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockBookFacadeImpl implements StockBookFacade {

    @Override
    public ObjectChangedDTO<InventoryBookDTO> add(StockBookDTO dto) {
        return null;
    }

    @Override
    public ObjectChangedDTO<InventoryBookDTO> update(StockBookDTO dto) {
        return null;
    }

    @Override
    public ObjectChangedDTO<InventoryBookDTO> delete(InventoryBookSearchReqDTO searchReq) {
        return null;
    }

    @Override
    public InventoryBookDTO get(InventoryBookSearchReqDTO searchReqDTO) {
        return null;
    }

    @Override
    public ListForPageDTO<InventoryBookDTO> getAll(InventoryBookSearchReqDTO searchReqDTO) {
        return null;
    }
}
