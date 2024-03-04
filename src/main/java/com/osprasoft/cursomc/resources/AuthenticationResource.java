package com.osprasoft.cursomc.resources;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osprasoft.cursomc.domain.Cliente;
import com.osprasoft.cursomc.domain.UserSS;
import com.osprasoft.cursomc.dto.ClienteDTO;
import com.osprasoft.cursomc.dto.CredenciaisDTO;
import com.osprasoft.cursomc.dto.LoginResponseDTO;
import com.osprasoft.cursomc.repositories.ClienteRepository;
import com.osprasoft.cursomc.security.JwtUtil;
import com.osprasoft.cursomc.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid CredenciaisDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = jwtUtil.generateToken((UserSS) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid ClienteDTO cliente) {
        Cliente cli = this.clienteRepository.findByEmail(cliente.getEmail());
        if (cli != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(cli.getSenha());
        UserSS user = new UserSS(cli.getId(), cli.getEmail(), encryptedPassword, cli.getPerfis());
        //this.clienteRepository.save(user);
        return ResponseEntity.ok().build();
    } 

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity < Void > refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user);
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
