package group.securitystudy.feature.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


//사용자 엔티티는 UserDetails를 상속받아서 구현해야 함!!

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID code;

    // 이메일을 그냥 로그인 아이디로 쓰겠음
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;


    //여러 개인 경우 그냥 쉼표(,)로 구분함
    //ex) ADMIN의 auth는 "ROLE_ADMIN,ROLE_USER"
    @Column(name = "auth")
    private String auth;

    @Builder
    public Users(String email, String password, String auth) {
        this.email = email;
        this.password = password;
        this.auth = auth;
    }


    //---- 여기서부터는 UserDetails implements시 구현해야 하는 메서드들

    // 사용자가 가지고 있는 권한을 콜렉션 형태로 반환!!!
    // 단, 반환되는 컬렉션의 자료형은 GrantedAuthority를 상속한 것, 혹은 그 자신이 되어야 함(Collection<? extends GrantedAuthority> 의 의미)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> roles= new HashSet<>();

        // 유저 엔티티의 auth를 쉼표(,) 기준으로 잘라서 ROLE_ADMIN과 ROLE_USER를 roles에 추가
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return password;
    }

    // 계정(해당 사용자) 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
