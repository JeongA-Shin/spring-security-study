package group.securitystudy.feature.repository;

import group.securitystudy.feature.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //email를 통해 회원을 조회하기 위해
}
