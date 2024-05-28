package com.example.eurekahairdresser.repository;

import com.example.eurekahairdresser.dto.HairdresserDto;
import com.example.eurekahairdresser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserEntityRepo extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM user_entity WHERE realm_id = ?1",nativeQuery = true)
    List<UserEntity> AllUser(@Param("realmId") String realmId);


    @Query(value = "select first_name as firstname,last_name as lastname,user_attribute.value as phonenumber, user_entity.email as email " +
            "from user_entity\n" +
            "    join user_attribute on user_entity.id=user_attribute.user_id\n" +
            "    where realm_id = ?1", nativeQuery = true)
    List<HairdresserDto> allHairdresser(@Param("realmId") String realmId);

    @Query(value = "select first_name as firstname,last_name as lastname,user_attribute.value as phonenumber, user_entity.email as email " +
            "from user_entity\n" +
            "    join user_attribute on user_entity.id=user_attribute.user_id\n" +
            "    where realm_id = ?1 and user_entity.id= ?2", nativeQuery = true)
    HairdresserDto getHairdresserProfile(@Param("realmId") String realmId,@Param("userId") String userId);

    @Query(value = "select * from user_entity\n" +
            "where username=?1 AND realm_id=?2", nativeQuery = true)
    UserEntity getUserByUsername(@Param("username") String username, @Param("realm_id") String realm_id);

    @Query(value = "select * from user_entity\n" +
            "where id=?1 AND realm_id=?2", nativeQuery = true)
    UserEntity getUserById(@Param("id") String id, @Param("realm_id") String realm_id);

    @Query(value = "select value\n" +
            "    from user_attribute\n" +
            "    join user_entity on user_entity.id=user_attribute.user_id\n" +
            "    where user_id=?1", nativeQuery = true)
    String getPhoneNumberByUserId(@Param("id") String id);


    List<UserEntity> findAll();
}
