package es.trapasoft.clase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // PERMITE ACCESO PÚBLICO A LOGIN Y RECURSOS ESTÁTICOS
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/alumnos/**").hasAnyRole("ADMIN", "ALUMNO")
                        .requestMatchers("/profesor/**").hasRole("PROFESOR")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // IMPORTANTE: evitar redirecciones
                        .permitAll())
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    // AGREGAR USUARIOS DE PRUEBA (TEMPORAL)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails alumno = User.builder()
                .username("alumno")
                .password(passwordEncoder().encode("alumno"))
                .roles("ALUMNO")
                .build();

        UserDetails profesor = User.builder()
                .username("profesor")
                .password(passwordEncoder().encode("profesor"))
                .roles("PROFESOR")
                .build();

        return new InMemoryUserDetailsManager(admin, alumno, profesor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
