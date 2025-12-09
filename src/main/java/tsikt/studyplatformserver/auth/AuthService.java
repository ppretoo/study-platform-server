package tsikt.studyplatformserver.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tsikt.studyplatformserver.User;
import tsikt.studyplatformserver.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        String hash = passwordEncoder.encode(request.getPassword());
        userRepository.saveUser(request.getName(), request.getEmail(), hash);

        UserRepository.AuthUser authUser = userRepository.findAuthUserByEmail(request.getEmail());
        return new User(authUser.getId(), authUser.getName(), authUser.getEmail());
    }

    public User login(LoginRequest request) {
        UserRepository.AuthUser authUser = userRepository.findAuthUserByEmail(request.getEmail());
        if (authUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        boolean ok = passwordEncoder.matches(
                request.getPassword(),
                authUser.getPasswordHash()
        );

        if (!ok) {
            throw new IllegalArgumentException("Invalid password");
        }

        return new User(authUser.getId(), authUser.getName(), authUser.getEmail());
    }
}