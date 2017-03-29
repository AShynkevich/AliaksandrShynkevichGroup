package com.epam.lab.mentoring.sample;

import com.epam.lab.mentoring.orm.annotation.Query;

public interface IRepositorySample {

    @Query("SELECT id, name FROM USERS")
    void readUser();
}
