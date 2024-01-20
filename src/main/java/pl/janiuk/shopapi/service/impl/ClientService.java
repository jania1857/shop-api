package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Client;
import pl.janiuk.shopapi.exception.InvalidCredentialsException;
import pl.janiuk.shopapi.repository.ClientRepository;
import pl.janiuk.shopapi.repository.RoleRepository;
import pl.janiuk.shopapi.service.IClientService;
import pl.janiuk.shopapi.service.IJwtTokenService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final IJwtTokenService jwtTokenService;
    @Autowired
    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, IJwtTokenService jwtTokenService) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }
    @Override
    public Client create(String firstname, String lastname, String username, String password) {
        if (checkIfUserExists(username)) {
            throw new IllegalArgumentException();
        }
        String hashedPassword = passwordEncoder.encode(password);
        Client client = Client.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(hashedPassword)
                .roleId(1)
                .build();
        return clientRepository.save(client);
    }
    @Override
    public Optional<Client> single(int id) {
        return clientRepository.findById(id);
    }
    @Override
    public List<Client> list() {
        return clientRepository.findAll();
    }
    @Override
    public Optional<Client> changeRole(int clientId, int roleId) {
        Optional<Client> foundClient = clientRepository.findById(clientId);
        if (foundClient.isPresent() && roleRepository.existsById(roleId)) {
            Client client = foundClient.get();
            client.setRoleId(roleId);
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }
    @Override
    public String generateJwt(String username, String password) throws InvalidCredentialsException {

        Client client = clientRepository.findClientByUsername(username);
        if (client != null && passwordEncoder.matches(password, client.getPassword())) {
            return jwtTokenService.generateToken(client);
        } else {
            throw new InvalidCredentialsException();
        }
    }
    @Override
    public Boolean existsById(int id) {
        return clientRepository.existsById(id);
    }
    private boolean checkIfUserExists(String username) {
        return clientRepository.existsByUsername(username);
    }
}
