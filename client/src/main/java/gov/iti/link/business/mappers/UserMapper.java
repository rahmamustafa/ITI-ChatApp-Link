package gov.iti.link.business.mappers;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.persistence.entities.UserEntity;

public class UserMapper {

    public UserEntity dtoToEntity(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(user.getPhone());
        userEntity.setName(user.getName());
        return userEntity;
    }

    public UserDTO entityToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhone(user.getPhone());
        userDTO.setName(user.getName());
        return userDTO;
    }
}
