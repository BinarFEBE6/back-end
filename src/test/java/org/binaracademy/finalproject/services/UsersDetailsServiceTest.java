package org.binaracademy.finalproject.services;

import com.github.javafaker.Faker;
import org.binaracademy.finalproject.entity.RoleEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.repositories.UsersDetailsRepo;
import org.binaracademy.finalproject.services.impl.UserServiceImpl;
import org.binaracademy.finalproject.services.impl.UsersDetailsServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UsersDetailsServiceTest {

    @Mock
    UsersDetailsRepo repository;

    @Rule //initMocks
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    UsersDetailsServiceImpl userDetailsServiceImpl = new UsersDetailsServiceImpl();
    Faker faker;

    @Before
    public void setUp() {
        faker = new Faker(new Locale("en-US"));
    }

    @DisplayName("Create User Details Test")
    @Test
    void create() {
        assertNotNull(userDetailsServiceImpl);
        faker = new Faker(new Locale("en-US"));

        UserDetailsEntity newUser = new UserDetailsEntity(
                LocalDate.of(2022, 9, 1),
                "Mr. ",
                faker.address().streetAddress(),
                faker.name().name()
        );

        when(repository.save(any(UserDetailsEntity.class)))
                .thenReturn(newUser);

        assertEquals(userDetailsServiceImpl.create(newUser),newUser);
        Mockito.verify(repository, Mockito.times(1)).save(newUser);
    }


//    @Test
//    @DisplayName("Get User Details Test")
//    void findByUserid() {
//        assertNotNull(userDetailsServiceImpl);
//        faker = new Faker(new Locale("en-US"));
//
//        Set<RoleEntity> s = new HashSet<RoleEntity>();
//        UserEntity newUserEntity = new UserEntity(2L,faker.name().name(),faker.internet().emailAddress(),faker.internet().password(),"TEST",LocalDateTime.now(),LocalDateTime.now(),s);
//        UserDetailsEntity newUser = new UserDetailsEntity(1L,faker.name().name(),LocalDate.of(2022, 9, 1),"Mr.",faker.address().fullAddress(),2L,newUserEntity, LocalDateTime.now(),LocalDateTime.now());
//
//        when(repository.findUserDetailsByUserId(2L))
//                .thenReturn(Optional.of(newUser));
//
//        assertEquals(userDetailsServiceImpl.findByUserid(2L),newUser);
//        Mockito.verify(repository, Mockito.times(1)).findUserDetailsByUserId(2L);
//    }

}