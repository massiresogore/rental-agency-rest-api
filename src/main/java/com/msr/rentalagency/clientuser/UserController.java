package com.msr.rentalagency.clientuser;

import com.msr.rentalagency.clientuser.converter.UserDtoToUserConverter;
import com.msr.rentalagency.clientuser.converter.UserToUserDtoConverter;
import com.msr.rentalagency.clientuser.dto.UserDto;
import com.msr.rentalagency.system.Result;
import com.msr.rentalagency.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final UserToUserDtoConverter userToUserDtoConverter;

    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @PostMapping
    public Result addUser(@Valid  @RequestBody ClientUser clientUser)
    {
        return new Result(true, StatusCode.SUCCESS,"User create success",this.userService.save(clientUser));
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable int userId)
    {
        return new Result(true,StatusCode.SUCCESS,"Find user success",this.userToUserDtoConverter.convert(this.userService.findById(userId)));
    }

    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable("userId") int userId,@Valid @RequestBody UserDto userDto)
    {
        //Convert userDto to ClientUser
        ClientUser clientUser = this.userDtoToUserConverter.convert(userDto);

        //Update
       ClientUser updatedUser = this.userService.updateUser(userId,clientUser);

        return new Result(true,
                StatusCode.SUCCESS,
                "Update user success",
                this.userToUserDtoConverter.convert(updatedUser)
        );

    }

    @GetMapping
    public Result findAllUser()
    {
        List<ClientUser> userList = this.userService.findAll();

        List<UserDto> userDtoList = userList.stream().map(this.userToUserDtoConverter::convert).toList();

        return new Result(true,StatusCode.SUCCESS,"Find all user success",userList);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable int userId)
    {
        this.userService.delete(userId);
        return new Result(true,StatusCode.SUCCESS,"Delete user success");
    }
}
