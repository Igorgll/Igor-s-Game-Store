package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.UserModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start() {
        userRepository.deleteAll();

        userRepository.save(new UserModel(0L, "José da Silva", "joao@email.com.br", "12345678"));

        userRepository.save(new UserModel(0L, "Maria Silva", "manuela@email.com.br", "12345678"));

        userRepository.save(new UserModel(0L, "Paulo Silva", "adriana@email.com.br", "12345678"));

        userRepository.save(new UserModel(0L, "Roberto Rodrigues", "paulo@email.com.br", "12345678"));
    }

    @Test
    @Order(1)
    @DisplayName("Return one user")
    public void shouldReturnOneUser() {

        Optional<UserModel> usuario = userRepository.findByUser("manuela@email.com.br");
        assertTrue(usuario.get().getUser().equals("manuela@email.com.br"));
    }

    @Test
    @Order(2)
    @DisplayName("Retorna 3 usuarios")
    public void shouldReturnThreeUsers() {
        List<UserModel> usersList = userRepository.findAllByNameContainingIgnoreCase("Silva");
        assertEquals(3, usersList.size());
        assertTrue(usersList.get(0).getName().equals("José da Silva"));
        assertTrue(usersList.get(1).getName().equals("Maria Silva"));
        assertTrue(usersList.get(2).getName().equals("Paulo Silva"));
    }

}
