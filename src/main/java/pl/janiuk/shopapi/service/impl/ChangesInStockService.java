package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.ChangesInStock;
import pl.janiuk.shopapi.repository.ChangesInStockRepository;
import pl.janiuk.shopapi.service.IChangesInStockService;

import java.util.List;

@Service
public class ChangesInStockService implements IChangesInStockService {
    private final ChangesInStockRepository changesInStockRepository;
    @Autowired
    public ChangesInStockService(ChangesInStockRepository changesInStockRepository) {
        this.changesInStockRepository = changesInStockRepository;
    }
    @Override
    public List<ChangesInStock> list() {
        return changesInStockRepository.findAll();
    }
    @Override
    public ChangesInStock create(int productId, boolean deliverySale, int change) {
        ChangesInStock changesInStock = ChangesInStock.builder()
                .productId(productId)
                .deliverySale(deliverySale)
                .change(change)
                .build();
        return changesInStockRepository.save(changesInStock);
    }
    @Override
    public List<ChangesInStock> singleProduct(int productId) {
        return changesInStockRepository.findChangesInStockByProductId(productId);
    }
}
