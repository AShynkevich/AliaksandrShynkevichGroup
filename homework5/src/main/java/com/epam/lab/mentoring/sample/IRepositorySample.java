package com.epam.lab.mentoring.sample;

import com.epam.lab.mentoring.orm.annotation.Query;

import java.util.List;

public interface IRepositorySample {

    @Query("SELECT id, name FROM USERS WHERE id = ?")
    List<User> readUser(String id);
}
