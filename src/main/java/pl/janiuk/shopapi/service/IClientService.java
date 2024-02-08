package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    Client create(String firstname, String lastname, String username, String password);
    Optional<Client> single(int id);
    Optional<Client> singleByUsername(String username);
    List<Client> list();
    Optional<Client> changeRole(int clientId, int roleId);
    Boolean existsById(int id);
    Client findByUsername(String username);
}
