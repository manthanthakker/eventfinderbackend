package com.example.eventfinderbackend.repositories;

import com.example.eventfinderbackend.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE u.username=:username and u.password =:password") User
    findUserByCredentials(@Param("username") String p, @Param("password") String r);

}

