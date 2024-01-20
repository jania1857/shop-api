package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Client;

public interface IJwtTokenService {
    String generateToken(Client client);
}
