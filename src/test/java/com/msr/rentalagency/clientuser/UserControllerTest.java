package com.msr.rentalagency.clientuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.rentalagency.clientuser.dto.UserDto;
import com.msr.rentalagency.system.StatusCode;
import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    List<ClientUser> users = new ArrayList<>();


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
    void addUser() throws Exception {
        //Given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");

        String jsonUser = objectMapper.writeValueAsString(user);

        given(this.userService.save(Mockito.any(ClientUser.class))).willReturn(user);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(baseUrl+"/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("User create success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.roles").value("admin user"))
                .andExpect(jsonPath("$.data.username").value("Bassirou"));
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        //Given
        ClientUser user = new ClientUser();
        user.setId(1);
        user.setUsername("Bassirou");
        user.setPassword("Mario");
        user.setEnabled(true);
        user.setRoles("admin user");

        given(this.userService.findById(1)).willReturn(user);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find user success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.roles").value("admin user"))
                .andExpect(jsonPath("$.data.username").value("Bassirou"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        //Given
        given(this.userService.findById(99)).willThrow(new ObjectNotFoundException("user",99));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/users/99")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testUpdateSuccess() throws Exception {
       //Given
        UserDto userDto = new UserDto(1,"Molo","user",true);

        String json = objectMapper.writeValueAsString(userDto);

        ClientUser updatedUser = new ClientUser();
        updatedUser.setId(1);
        updatedUser.setUsername("Bassirou Updated");
        updatedUser.setRoles("user");
        updatedUser.setEnabled(false);

        given(this.userService.updateUser(eq(1),Mockito.any(ClientUser.class))).willReturn(updatedUser);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(baseUrl+"/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update user success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.roles").value("user"))
                .andExpect(jsonPath("$.data.username").value("Bassirou Updated"));


    }

    @Test
    void testUpdateWithNonExistentId() throws Exception {
        //Given
        UserDto userDto = new UserDto(1,"Molo","user",true);
        String json = objectMapper.writeValueAsString(userDto);


        given(this.userService.updateUser(eq(99),Mockito.any(ClientUser.class))).willThrow(new ObjectNotFoundException("user",99));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(baseUrl+"/users/99")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllSuccess() throws Exception {
        //Given
        given(this.userService.findAll()).willReturn(this.users);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all user success"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].roles").value("admin user"))
                .andExpect(jsonPath("$.data[0].username").value("Bassirou"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].roles").value("user"))
                .andExpect(jsonPath("$.data[1].username").value("Obamé yang"));

    }

    @Test
    void testDeleteSuccess() throws Exception {
        //Given
        doNothing().when(this.userService).delete(1);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl+"/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete user success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteWithIdNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("user",99)).when(this.userService).delete(99);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl+"/users/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with id:99 :)"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}