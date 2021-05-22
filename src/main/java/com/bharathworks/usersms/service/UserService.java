package com.bharathworks.usersms.service;

import com.bharathworks.usersms.entity.UserEntity;
import com.bharathworks.usersms.repository.UserRepository;
import com.bharathworks.usersms.vo.DepartmentDetailsDTO;
import com.bharathworks.usersms.vo.ResponseVo;
import com.bharathworks.usersms.vo.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    private UserEntity convertDtoToEntity(UserDetailsDTO userDetailsDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDetailsDTO.getUserId());
        userEntity.setUserName(userDetailsDTO.getUserName());
        userEntity.setDepartmentId(userDetailsDTO.getDepartmentId());
        return userEntity;
    }

    public UserDetailsDTO saveUsers(UserDetailsDTO userDetailsDTO) {
        if (!ObjectUtils.isEmpty(userDetailsDTO)) {
            UserEntity requestEntity = convertDtoToEntity(userDetailsDTO);
            UserEntity entityFromDatabase = userRepository.findByUserId(requestEntity.getUserId());
            if (!ObjectUtils.isEmpty(entityFromDatabase)) {
                if (entityFromDatabase.getUserId().equals(requestEntity.getUserId())) {
                    entityFromDatabase.setUserName(requestEntity.getUserName());
                    entityFromDatabase.setDepartmentId(requestEntity.getDepartmentId());
                    userRepository.save(entityFromDatabase);
                }
            } else {
                userRepository.save(requestEntity);
            }
        }
        return userDetailsDTO;
    }

    public ResponseVo getUserDetails(Long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        ResponseVo responseVo= new ResponseVo();

        if (!ObjectUtils.isEmpty(userEntity)) {
            userDetailsDTO.setUserId(userEntity.getUserId());
            userDetailsDTO.setUserName(userEntity.getUserName());
            userDetailsDTO.setDepartmentId(userEntity.getDepartmentId());
            responseVo.setUserDetailsDTO(userDetailsDTO);
        }

        DepartmentDetailsDTO departmentDetailsDTO =
                restTemplate.getForObject("http://DEPARTMENT-SERVICE:9001/departments/"+userDetailsDTO.getDepartmentId(), DepartmentDetailsDTO.class);

        responseVo.setDepartmentDetailsDTO(departmentDetailsDTO);
        return responseVo;
    }
}
