package com.msr.rentalagency.clientuser;

import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    List<ClientUser> users = new ArrayList();

    @BeforeEach
    void setUp() {
        ClientUser user1 = new ClientUser();
        user1.setId(1);
        user1.setUsername("Bassirou");
        user1.setPassword("Mario");
        user1.setEnabled(true);
        user1.setRoles("admin user");
        users.add(user1);

        ClientUser user2 = new ClientUser();
        user2.setId(2);
        user2.setUsername("Obamé yang");
        user2.setPassword("123456");
        user2.setEnabled(true);
        user2.setRoles("user");
        users.add(user2);

        ClientUser user3 = new ClientUser();
        user3.setId(3);
        user3.setUsername("Treifle ");
        user3.setPassword("123456");
        user3.setEnabled(true);
        user3.setRoles("admin user");
        users.add(user3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        //Given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");
        given(this.userRepository.save(user)).willReturn(user);

        //When
        ClientUser saveUser = this.userService.save(user);


        //Then
        assertThat(saveUser.getId()).isEqualTo(user.getId());
        assertThat(saveUser.getRoles()).isEqualTo(user.getRoles());
        assertThat(saveUser.getPassword()).isEqualTo(user.getPassword());

        verify(this.userRepository,times(1)).save(user);
    }

    @Test
    void testFindByIdSuccess()
    {
        //Given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");
        given(this.userRepository.findById(1)).willReturn(Optional.of(user));

        //When
        ClientUser foundUser= this.userService.findById(1);

        //Then
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getRoles()).isEqualTo(user.getRoles());
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());

        verify(this.userRepository,times(1)).findById(1);

    }

    @Test
    void testFindbyIdNotFound()
    {
        //Given
        given(this.userRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(()->{
          ClientUser returnedUser =   this.userService.findById(99);
        });

        //Then
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id:99 :)");
        verify(this.userRepository,times(1)).findById(Mockito.any(Integer.class));
    }

    @Test
    void testUpdateUserSuccesss()
    {
        //given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");

        ClientUser update = new ClientUser();
        update.setRoles("user");
        update.setUsername("Bassirou update");

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));
        given(this.userRepository.save(user)).willReturn(user);

        //When
        ClientUser updatedUser = this.userService.updateUser(1,update);

        //Then
        assertThat(updatedUser.getUsername()).isEqualTo(update.getUsername());
        assertThat(updatedUser.getRoles()).isEqualTo(update.getRoles());

        verify(this.userRepository,times(1)).findById(1);
        verify(this.userRepository,times(1)).save(user);
    }

    @Test
    void testUpdateUserWithNoExistentId()
    {
        //given
        ClientUser user = new ClientUser();
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");
        given(this.userRepository.findById(1)).willReturn(Optional.empty());

        //When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.userService.updateUser(1, user);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id:1 :)");
        verify(this.userRepository, times(1)).findById(1);

    }

    @Test
    void testFindAllsuccess()
    {
        //Given
        given(this.userRepository.findAll()).willReturn(this.users);

        //When
        List<ClientUser> actualListUser = this.userService.findAll();

        //Then
        assertThat(actualListUser.size()).isEqualTo(this.users.size());

        verify(this.userRepository,times(1)).findAll();
    }

    @Test
    void testDeleteSuccess()
    {
        //Given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));
        doNothing().when(this.userRepository).deleteById(1);

        //When
        this.userService.delete(1);

        //Then
        verify(this.userRepository,times(1)).deleteById(1);
    }

    @Test
    void testDeleteWithIdNotFound()
    {
        //Given
     given(this.userRepository.findById(99)).willReturn(Optional.empty());

        //When
        Throwable thrown = assertThrows(ObjectNotFoundException.class,()->{
            this.userService.delete(99);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id:99 :)");
        verify(this.userRepository,times(1)).findById(99);
    }
}