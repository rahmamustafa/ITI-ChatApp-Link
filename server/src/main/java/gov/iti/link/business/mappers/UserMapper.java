package gov.iti.link.business.mappers;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.persistence.entities.UserEntity;

public class UserMapper {

    public UserEntity dtoToEntity(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(user.getPhone());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBio(user.getBio());
        userEntity.setCountry(user.getCountry());
        userEntity.setDate(user.getDate());
        userEntity.setGender(user.getGender());
        userEntity.setPassword(user.getPassword());
        userEntity.setPicture(user.getPicture());
        return userEntity;
    }

    public UserDTO entityToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhone(user.getPhone());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBio(user.getBio());
        userDTO.setCountry(user.getCountry());
        userDTO.setDate(user.getDate());
        userDTO.setGender(user.getGender());
        userDTO.setPassword(user.getPassword());
        userDTO.setPicture(user.getPicture());
        return userDTO;
    }

   
}
