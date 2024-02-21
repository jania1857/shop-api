package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.ChangesInStatus;
import pl.janiuk.shopapi.domain.Status;
import pl.janiuk.shopapi.repository.ChangesInStatusRepository;
import pl.janiuk.shopapi.repository.StatusRepository;
import pl.janiuk.shopapi.service.IStatusService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusService implements IStatusService {
    private final ChangesInStatusRepository changesInStatusRepository;
    private final StatusRepository statusRepository;
    @Autowired
    public StatusService(ChangesInStatusRepository changesInStatusRepository, StatusRepository statusRepository) {
        this.changesInStatusRepository = changesInStatusRepository;
        this.statusRepository = statusRepository;
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

    @Override
    public List<Status> list() {
        return statusRepository.findAll();
    }
}
