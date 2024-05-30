package org.socketio.demo.domain.service;


import org.socketio.demo.domain.entity.AuthUser;
import org.socketio.demo.domain.model.AuthUserModel;
import org.socketio.demo.domain.repository.AuthUserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    final private AuthUserRepository authUserRepository;
    final private PasswordEncoder passwordEncoder;
//    final private Authentication authentication;
    /**
     * @see AuthUser
     * @param authUser 유저 객체 Required
     * @return void
     * @throws DataIntegrityViolationException 데이터 무결성 위반 예외
     * @throws EntityExistsException UserName(ID)가 이미 존재하는 예외
     * @throws Exception 기타 예외
     * @apiNote Method function sign-up for user who has UserName(ID) and Password
     */
    public void joinUser(AuthUser authUser) throws Exception {
        try {

            String encodedPassword = passwordEncoder.encode(authUser.getPassword());
            authUser.setPassword(encodedPassword);
            if(authUserRepository.existsById(authUser.getUsername())){
                throw new EntityExistsException("이미 존재하는 ID");
            }
            authUserRepository.save(authUser);

        }
        catch (DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            throw new DataIntegrityViolationException(e.getMessage()+"데이터 제약 조건 위반");

        }
        catch (EntityExistsException e){
            throw new EntityExistsException(e.getMessage()+"이미 존재하는 ID");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage()+"기타 에러");
        }
//        catch (DataAccessException e){
//            throw new DataAccessException();
//        }

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(username);
        if (authUser.isPresent()) {
            AuthUser auth = authUser.get();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new AuthUserModel(auth.getUsername(), auth.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


}
