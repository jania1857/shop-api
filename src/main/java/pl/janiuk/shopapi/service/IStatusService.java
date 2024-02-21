package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.ChangesInStatus;
import pl.janiuk.shopapi.domain.Status;

import java.util.List;

public interface IStatusService {
    void changeStatus(int statusId, int orderId);
    List<ChangesInStatus> singleOrderStatuses(int orderId);
    List<Status> list();
}
