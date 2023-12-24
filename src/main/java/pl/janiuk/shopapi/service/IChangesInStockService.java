package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.ChangesInStock;

import java.util.List;

public interface IChangesInStockService {
    List<ChangesInStock> list();
    ChangesInStock create(int productId, boolean deliverySale, int change);
    List<ChangesInStock> singleProduct(int productId);
}
