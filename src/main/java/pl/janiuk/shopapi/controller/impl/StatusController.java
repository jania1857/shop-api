package pl.janiuk.shopapi.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IStatusController;
import pl.janiuk.shopapi.domain.ChangesInStatus;
import pl.janiuk.shopapi.domain.Status;
import pl.janiuk.shopapi.dto.status.ChangeInStatusRequest;
import pl.janiuk.shopapi.dto.status.ChangeInStatusResponse;
import pl.janiuk.shopapi.dto.status.StatusResponse;
import pl.janiuk.shopapi.service.IStatusService;

import java.util.List;

@RestController
public class StatusController implements IStatusController {
    private final IStatusService statusService;

    public StatusController(IStatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public ResponseEntity<?> changeStatus(ChangeInStatusRequest request) {
        if (request.statusId() < 1 || request.statusId() > 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        statusService.changeStatus(request.statusId(), request.orderId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ChangeInStatusResponse>> singleOrderChanges(int orderId) {
        List<ChangesInStatus> changes = statusService.singleOrderStatuses(orderId);
        List<ChangeInStatusResponse> response = changes.stream().map(this::changeInStatusToChangeInStatusResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<StatusResponse>> listStatuses() {
        List<Status> statuses = statusService.list();
        List<StatusResponse> response = statuses.stream().map(this::statusToStatusResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ChangeInStatusResponse changeInStatusToChangeInStatusResponse(ChangesInStatus changesInStatus) {
        return new ChangeInStatusResponse(
                changesInStatus.getId(),
                changesInStatus.getChangeDate(),
                changesInStatus.getOrderId(),
                changesInStatus.getStatusByStatusId().getName()
        );
    }

    private StatusResponse statusToStatusResponse(Status status) {
        return new StatusResponse(
                status.getId(),
                status.getName()
        );
    }
}
