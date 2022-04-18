package br.com.gestao.financeira;

import br.com.gestao.financeira.http.request.UserRequest;
import br.com.gestao.financeira.models.Role;
import br.com.gestao.financeira.services.UserService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class GestaoFinanceiraApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoFinanceiraApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//          userService.saveRole(new Role(null, "ROLE_USER"));
//          userService.saveRole(new Role(null, "ROLE_ADMIN"));
//          userService.saveRole(new Role(null, "ROLE_MANAGER"));
//          userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//          userService.create(new UserRequest(null, "John Travolta", "john@email.com", "1234"));
//          userService.create(new UserRequest(null, "Will Smith", "will@email.com", "1234"));
//          userService.create(new UserRequest(null, "Jim Carry", "jim@email.com", "1234"));
//          userService.create(new UserRequest(null, "Arnold Schwarzenegger", "arnold@email.com", "1234"));
//
//          userService.addRoleToUser("john@email.com", "ROLE_USER");
//          userService.addRoleToUser("john@email.com", "ROLE_MANAGER");
//          userService.addRoleToUser("will@email.com", "ROLE_MANAGER");
//          userService.addRoleToUser("jim@email.com", "ROLE_ADMIN");
//          userService.addRoleToUser("arnold@email.com", "ROLE_SUPER_ADMIN");
//          userService.addRoleToUser("arnold@email.com", "ROLE_ADMIN");
//          userService.addRoleToUser("arnold@email.com", "ROLE_USER");
//        };
//    }

}
