package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.ChangesInStatus;
import pl.janiuk.shopapi.repository.ChangesInStatusRepository;
import pl.janiuk.shopapi.service.IStatusService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusService implements IStatusService {
    private final ChangesInStatusRepository changesInStatusRepository;
    @Autowired
    public StatusService(ChangesInStatusRepository changesInStatusRepository) {
        this.changesInStatusRepository = changesInStatusRepository;
    }

    @Override
    public void changeStatus(int statusId, int orderId) {
        Date now = Date.valueOf(LocalDateTime.now().toLocalDate());
        ChangesInStatus change = ChangesInStatus.builder()
                .statusId(statusId)
                .orderId(orderId)
                .changeDate(now)
                .build();
        changesInStatusRepository.save(change);
    }

    @Override
    public List<ChangesInStatus> singleOrderStatuses(int orderId) {
        return changesInStatusRepository.findChangesInStatusByOrderId(orderId);
    }
}
