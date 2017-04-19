package com.epam.lab.mentoring;

import com.epam.lab.mentoring.api.IBasicService;

public class BasicService implements IBasicService {
    @Override
    public String provideMessage() {
        return "Hello from service";
    }
}
