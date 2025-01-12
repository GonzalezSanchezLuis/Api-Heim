package com.holi.api.users.application.service;

import com.holi.api.users.application.dto.UserRequest;
import com.holi.api.users.application.dto.UserResponse;
import com.holi.api.users.application.mapper.UserMapper;
import com.holi.api.users.domain.entity.User;
import com.holi.api.users.infraestructure.exceptions.EmailAlreadyRegisteredException;
import com.holi.api.users.infraestructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }


    public UserResponse registerUser(UserRequest userRequest) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("Ya existe un usuario registrado con ese email.");
        } else {
            //System.out.println(userRequest);
            String encodePassword = bcryptPasswordEncoder.encode(userRequest.getPassword());
            userRequest.setPassword(encodePassword);
            User newUser = userMapper.toEntity(userRequest);
            newUser.setActive(true);
            newUser.setRole("USER");

            //System.out.println("Mapped User: " + newUser);
            return  userMapper.toResponse(userRepository.save(newUser));


        }
    }

    public UserResponse getUserById(Long userId) throws NoSuchElementException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return userMapper.toResponse(user);
    }

    public UserResponse updateUserData(Long userId, UserRequest userRequest) {
        // Buscar el usuario por ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        // Actualizar los campos del usuario si los valores no son nulos o vacíos
        if (userRequest.getFullName() != null && !userRequest.getEmail().isEmpty()) {
            user.setFullName(userRequest.getFullName());
        }
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            // Validar si el email ya está en uso por otro usuario
            Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getUserId().equals(userId)) {
                throw new IllegalArgumentException("El email ya está en uso por otro usuario.");
            }
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPhone() != null && !userRequest.getPhone().isEmpty()) {
            user.setPhone(userRequest.getPhone());
        }
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            // Asegúrate de hacer el hashing de la contraseña si no lo has hecho
            user.setPassword(userRequest.getPassword());  // Considera encriptar la contraseña antes de guardarla
        }

        // Guardar el usuario actualizado en la base de datos
        userRepository.save(user);

        // Convertir el objeto User actualizado en un UserResponse y devolverlo
        return userMapper.toResponse(user);
    }


    public void userDelete(Long userId){
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        userRepository.delete(userToDelete);

    }
}
