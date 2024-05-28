package com.example.eurekaclient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String email;

    private String email_constraint;

    private boolean email_verified;

    private boolean enabled;

    private String federation_link;

    private String last_name;

    private String first_name;

    private String realm_id;

    private String username;

    private Long created_timestamp;

    private String service_account_client_link;

    private Integer not_before;

}
