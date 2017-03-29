package com.epam.lab.mentoring.sample;

import com.epam.lab.mentoring.orm.annotation.Query;

public interface IRepositorySample {

    @Query("SELECT id, name FROM USERS WHERE id = ?")
    User readUser(String id);

    @Query("DELETE FROM USERS WHERE id = ?")
    void deleteUser(String id);

    @Query("UPDATE USERS SET name = ? WHERE id = ?")
    void updateUser(String id, String name);

    @Query("INSERT INTO USERS (id, name) VALUES (?, ?)")
    void insertUser(String id, String name);
}
