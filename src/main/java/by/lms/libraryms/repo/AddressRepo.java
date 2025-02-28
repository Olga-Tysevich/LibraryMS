package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Address;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface AddressRepo extends MongoRepository<Address, String> {
    List<Address> findByCity(String city);
    List<Address> findByStreet(String street);
    List<Address> findByCityAndStreet(String city, String street);
    List<Address> findByCityAndStreetAndHouseNumber(String city, String street, String houseNumber);
    List<Address> findByCityAndStreetAndHouseNumberAndApartmentNumber(String city, String street, String houseNumber, int apartmentNumber);
    List<Address> findByPostalCode(String postalCode, Sort sort);
    List<Address> findByUserIds(Set<ObjectId> userIds);
}
