package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;

public interface UserService {
    UserDTO save(UserDTO user);
    UserDTO findByPhone(String phone);
}
