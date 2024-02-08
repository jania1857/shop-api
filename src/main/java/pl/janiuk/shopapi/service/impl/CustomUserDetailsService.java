package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Client;
import pl.janiuk.shopapi.dto.client.details.CustomUserDetails;
import pl.janiuk.shopapi.repository.ClientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;
    @Autowired
    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByUsername(username);
        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(client);
    }
}
