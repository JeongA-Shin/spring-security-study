package group.securitystudy.feature.repository;

import group.securitystudy.feature.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email); //email를 통해 회원을 조회하기 위해
}
