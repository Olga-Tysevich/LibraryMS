package by.lms.libraryms.repo;

import by.lms.libraryms.domain.RoleEnum;
import by.lms.libraryms.domain.auth.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByPhoneIn(Collection<Set<String>> phones);

    List<User> findByAddressIdsIn(Collection<Set<ObjectId>> addressIds);

    List<User> findByRolesIn(Collection<Set<RoleEnum>> roles);

}
