package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.status.ChangeInStatusRequest;
import pl.janiuk.shopapi.dto.status.ChangeInStatusResponse;
import pl.janiuk.shopapi.dto.status.StatusResponse;

import java.util.List;

@RequestMapping("/api/v1/status")
public interface IStatusController {
    @PostMapping
    ResponseEntity<?> changeStatus(@RequestBody ChangeInStatusRequest request);
    @GetMapping("/{orderId}")
    ResponseEntity<List<ChangeInStatusResponse>> singleOrderChanges(@PathVariable int orderId);
    @GetMapping
    ResponseEntity<List<StatusResponse>> listStatuses();
}
