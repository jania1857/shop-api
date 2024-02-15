package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.ChangesInStatus;

import java.util.List;

public interface IStatusService {
    void changeStatus(int statusId, int orderId);
    List<ChangesInStatus> singleOrderStatuses(int orderId);
}
