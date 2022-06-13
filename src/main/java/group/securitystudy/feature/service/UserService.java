package group.securitystudy.feature.service;

import group.securitystudy.feature.model.Users;
import group.securitystudy.feature.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


// UserDetailService를 상속받고 필수 메소드인 loadUserByUsername()를 구현 -

// 그런데 내가 User 에도 썼듯이 현재 로그인 아이디 칼럼이 따로 없고 그냥 이메일을 아이디처럼 쓸 것임
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    /**
     *
     * @param email 원래는 username이 맞음. 원래 loadUserByUsername()는 유저 네임으로 해당 유저를 찾음
     *              그런데 나는 지금 User에도 썼듯이 그냥 이메일을 아이디로 쓰고 있으므로 이메일을 기준으로 조회함
     * @return - > 원래는 UserDetails가 기본 반환 타입임(그냥 젤 처음에 implements override해서 불러왔을 때). 
     *             그런데 현재 나의 User 엔티티는 UserDetails을 상속받아 구현하고 있기 때문에 (자동으로) 다운캐스팅이 가능함
     *             그래서 내가 만든 엔티티인 User을 반환형으로 하게 함
     * @throws UsernameNotFoundException
     */
    @Override
    public Users loadUserByUsername(String email) throws UsernameNotFoundException {
        //null이면 UsernameNotFoundException 예외를 발생시키고, null이 아니면 UserInfo를 반환하게 처리
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    /**
     * 회원 정보 저장
     * @param user
     * @return 저장된 user 객체
     */

    public Users save(Users user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //회원 정보로 넘어오는 비밀번호는 반드시 암호화된 상태로 db에 저장되어야 한다
        //즉, 사용자로부터 입력받은 패스워드를 BCrypt로 암호화한 후에 회원을 저장
        user.setPassword(encoder.encode(user.getPassword()));

        return repository.save(user);

    }
}
