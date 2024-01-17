package com.msr.rentalagency.clientuser;

import com.msr.rentalagency.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClientUser save(ClientUser clientUser)
    {
        return this.userRepository.save(clientUser);
    }

    public ClientUser findById(int userId)
    {
        return this.userRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));
    }

    public ClientUser updateUser(int userId,ClientUser updatedUser)
    {
       return   this.userRepository.findById(userId)
                .map(oldUser->{
                    oldUser.setUsername(updatedUser.getUsername());
                    oldUser.setRoles(updatedUser.getRoles());
                    oldUser.setEnabled(updatedUser.isEnabled());
                  return   this.userRepository.save(oldUser);
                }).orElseThrow(()->new ObjectNotFoundException("user",userId));

    }

    public List<ClientUser> findAll()
    {
        return new ArrayList<>(this.userRepository.findAll());
    }

    public void delete(int userId)
    {
        this.userRepository.findById(userId)
               .orElseThrow(()->new ObjectNotFoundException("user",userId));

       this.userRepository.deleteById(userId);
    }
}
