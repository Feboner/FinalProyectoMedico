package com.duoc.msauth.service;
import com.duoc.msauth.model.Usuario;
import com.duoc.msauth.repository.UsuarioRepository;
import com.duoc.msauth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class AuthService {
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    public String login(String u, String p) {
        Optional<Usuario> user = usuarioRepository.findByUsername(u);
        if (user.isPresent() && passwordEncoder.matches(p, user.get().getPassword())) {
            return jwtUtil.generateToken(u);
        }
        return null;
    }
}
