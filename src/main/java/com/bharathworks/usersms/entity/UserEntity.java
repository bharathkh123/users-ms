package com.bharathworks.usersms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="USERS")
public class UserEntity {
    @Id
    @Column(name = "ID")
    private Long userId;
    @Column(name="NAME")
    private String userName;
    @Column(name="DEPARTMENT_ID")
    private Long departmentId;
}
