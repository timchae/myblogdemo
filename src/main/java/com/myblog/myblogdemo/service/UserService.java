package com.myblog.myblogdemo.service;

import com.myblog.myblogdemo.dto.SignupRequestDto;
import com.myblog.myblogdemo.model.User;
import com.myblog.myblogdemo.model.UserRole;
import com.myblog.myblogdemo.repository.UserRepository;
import com.myblog.myblogdemo.security.kakao.KakaoOAuth2;
import com.myblog.myblogdemo.security.kakao.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KakaoOAuth2 kakaoOAuth2, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kakaoOAuth2 = kakaoOAuth2;
        this.authenticationManager = authenticationManager;
    }

    public String checkName(SignupRequestDto requestDto) {
        String pattern = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{3,20}$";
        String username = requestDto.getUsername();
        String errMsg = "";
        if (username.isEmpty()) {
            errMsg = "닉네임을 입력해 주세요.";
            throw new IllegalArgumentException("닉네임을 입력해 주세요.");
        } else {
            Matcher match = Pattern.compile(pattern).matcher(username);
            if (!match.find()) {
                errMsg = "닉네임은 숫자와 영문자 조합으로 3~10자리를 사용해야 합니다.";
                throw new IllegalArgumentException("닉네임은 숫자와 영문자 조합으로 3~20자리를 사용해야 합니다.");
            } else {
                return errMsg;
            }
        }
    }

    public void checkPassword(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String passwordNen = requestDto.getPassword();
        String passwordConfirm = requestDto.getPasswordconfirm();
        if (!passwordNen.isEmpty() && !passwordConfirm.isEmpty()) {
            if (passwordNen.length() >= 4 && passwordNen.length() <= 20) {
                if (passwordNen.indexOf(username) != -1) {
                    throw new IllegalArgumentException("비밀번호에 닉네임을 포함할 수 없습니다.");
                } else if (!passwordNen.equals(passwordConfirm)) {
                    throw new IllegalArgumentException("패스워드가 일치하지 않습니다!");
                }
            } else {
                throw new IllegalArgumentException("비밀번호는  4~20자리를 사용해야 합니다.");
            }
        } else {
            throw new IllegalArgumentException("패스워드를 입력해 주세요.");
        }
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        String passwordconfirm = passwordEncoder.encode(requestDto.getPasswordconfirm());
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, passwordconfirm, email, role);
        userRepository.save(user);
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;

            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}