package epam.com.task_rest_boot.repository;

import epam.com.task_rest_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select count(u.id) from User u where u.firstName = :firstName and u.lastName = :lastName")
    Integer countByFirstNameAndLastName(String firstName, String lastName);
}
