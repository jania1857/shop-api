package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.changesinstock.ChangesInStockResponse;
import pl.janiuk.shopapi.dto.changesinstock.CreateChangesInStockRequest;

import java.util.List;

@RequestMapping("/api/v1/changes_in_stock")
public interface IChangesInStockController {
    @PostMapping
    ResponseEntity<ChangesInStockResponse> createChangeInStock(@RequestBody CreateChangesInStockRequest request);
    @GetMapping("/{productId}")
    ResponseEntity<List<ChangesInStockResponse>> getProductChanges(@PathVariable int productId);
    @GetMapping
    ResponseEntity<List<ChangesInStockResponse>> listChanges();

}
