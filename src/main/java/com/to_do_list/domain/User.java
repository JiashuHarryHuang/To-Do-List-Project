package com.to_do_list.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;
}
