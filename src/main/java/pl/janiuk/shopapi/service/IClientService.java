package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Client;
import pl.janiuk.shopapi.exception.InvalidCredentialsException;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    Client create(String firstname, String lastname, String username, String password);
    Optional<Client> single(int id);
    List<Client> list();
    Optional<Client> changeRole(int clientId, int roleId);
    String generateJwt(String username, String password) throws InvalidCredentialsException;
    Boolean existsById(int id);
}
