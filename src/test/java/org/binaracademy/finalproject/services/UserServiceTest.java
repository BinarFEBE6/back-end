package org.binaracademy.finalproject.services;

import com.github.javafaker.Faker;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserRepo repository;

    @InjectMocks
    UserServiceImpl userServiceImpl = new UserServiceImpl();
    Faker faker;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); //without this you will get NPE
        faker = new Faker(new Locale("en-US"));
    }

    @Test
    @DisplayName("Create User Test")
    public void create(){
        assertNotNull(userServiceImpl);

        UserEntity newUser = new UserEntity(
                faker.name().name(),
                faker.internet().emailAddress(),
                faker.internet().password()
        );

        when(repository.save(any(UserEntity.class)))
                .thenReturn(newUser);

        assertEquals(userServiceImpl.create(newUser),newUser);
        Mockito.verify(repository, Mockito.times(1)).save(newUser);

    }

    @Test
    @DisplayName("Get User By Email Test")
    public void getUserByEmail() {
        assertNotNull(userServiceImpl);

        String userEmail = faker.internet().emailAddress();
        UserEntity newUser = new UserEntity(
                faker.name().name(),
                userEmail,
                faker.internet().password()
        );

        when(repository.findUserByEmail(userEmail))
                .thenReturn(newUser);

        assertEquals(userServiceImpl.getUserByEmail(userEmail),newUser);
        Mockito.verify(repository, Mockito.times(1)).findUserByEmail(userEmail);
    }
}