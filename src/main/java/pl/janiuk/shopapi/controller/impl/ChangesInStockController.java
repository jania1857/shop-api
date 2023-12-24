package pl.janiuk.shopapi.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IChangesInStockController;
import pl.janiuk.shopapi.domain.ChangesInStock;
import pl.janiuk.shopapi.dto.changesinstock.ChangesInStockResponse;
import pl.janiuk.shopapi.dto.changesinstock.CreateChangesInStockRequest;
import pl.janiuk.shopapi.service.IChangesInStockService;

import java.util.List;

@RestController
public class ChangesInStockController implements IChangesInStockController {
    private final IChangesInStockService changesInStockService;
    @Autowired
    public ChangesInStockController(IChangesInStockService changesInStockService) {
        this.changesInStockService = changesInStockService;
    }
    @Override
    public ResponseEntity<ChangesInStockResponse> createChangeInStock(CreateChangesInStockRequest request) {
        ChangesInStock changesInStock = changesInStockService.create(request.productId(), request.deliverySale(),
                request.change());
        ChangesInStockResponse response = changesInStockToChangesInStockResponse(changesInStock);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<List<ChangesInStockResponse>> getProductChanges(int productId) {
        List<ChangesInStock> changesInStock = changesInStockService.singleProduct(productId);
        List<ChangesInStockResponse> response = changesInStock.stream().map(this::changesInStockToChangesInStockResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<ChangesInStockResponse>> listChanges() {
        List<ChangesInStock> changesInStock = changesInStockService.list();
        List<ChangesInStockResponse> response = changesInStock.stream().map(this::changesInStockToChangesInStockResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private ChangesInStockResponse changesInStockToChangesInStockResponse(ChangesInStock changesInStock) {
        return new ChangesInStockResponse(
                changesInStock.getId(),
                changesInStock.getProductId(),
                changesInStock.getChangeDate(),
                changesInStock.isDeliverySale(),
                changesInStock.getChange()
        );
    }
}
