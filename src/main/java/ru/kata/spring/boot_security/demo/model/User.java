package ru.kata.spring.boot_security.demo.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private String lastName;
    private Byte age;

}
