package peaksoft.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import peaksoft.entity.SecurityUser;
import peaksoft.entity.User;
import peaksoft.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUsersByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("not fount");
        }
        return new SecurityUser(user);
    }
}
