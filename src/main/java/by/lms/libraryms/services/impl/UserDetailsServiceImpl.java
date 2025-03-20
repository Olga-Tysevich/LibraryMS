package by.lms.libraryms.services.impl;

import by.lms.libraryms.exceptions.ObjectDoesNotExistException;
import by.lms.libraryms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the UserDetailsService interface and is used to load user details by username.
 * It uses UserRepository for retrieving user details from the database.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * UserRepository bean.
     *
     * @see UserRepo
     */
    private final UserRepo userRepository;


    /**
     * This method loads user details by the given email.
     *
     * @param email The email of the user to load.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException If user with the given email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(ObjectDoesNotExistException::new);
    }
}