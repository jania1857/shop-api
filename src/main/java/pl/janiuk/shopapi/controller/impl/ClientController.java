package pl.janiuk.shopapi.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IClientController;
import pl.janiuk.shopapi.domain.Client;
import pl.janiuk.shopapi.dto.client.ClientResponse;
import pl.janiuk.shopapi.dto.client.LoginRequest;
import pl.janiuk.shopapi.dto.client.LoginResponse;
import pl.janiuk.shopapi.dto.client.RegisterRequest;
import pl.janiuk.shopapi.exception.InvalidCredentialsException;
import pl.janiuk.shopapi.service.IClientService;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController implements IClientController {
    private final IClientService clientService;
    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }
    @Override
    public ResponseEntity<ClientResponse> register(RegisterRequest request) {
        try {
            Client savedClient = clientService.create(
                    request.firstname(),
                    request.lastname(),
                    request.username(),
                    request.password());
            ClientResponse response = new ClientResponse(
                    savedClient.getId(),
                    savedClient.getUsername(),
                    savedClient.getFirstname(),
                    savedClient.getLastname(),
                    "ROLE_CLIENT"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        try {
            LoginResponse response = new LoginResponse(
                    clientService.generateJwt(request.username(), request.password())
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<List<ClientResponse>> listClients() {
        List<Client> clientList = clientService.list();
        List<ClientResponse> response = clientList.stream().map(this::clientToClientResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ClientResponse> singleClient(int clientId) {
        Optional<Client> optionalClient = clientService.single(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            ClientResponse response = clientToClientResponse(client);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<ClientResponse> changeClientRole(int clientId, int roleId) {
        Optional<Client> optionalClient = clientService.changeRole(clientId, roleId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            ClientResponse response = clientToClientResponse(client);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ClientResponse clientToClientResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getUsername(),
                client.getFirstname(),
                client.getLastname(),
                client.getRoleByRoleId().getRole()
        );
    }
}
