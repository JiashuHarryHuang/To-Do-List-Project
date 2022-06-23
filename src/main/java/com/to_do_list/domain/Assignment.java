package com.to_do_list.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    private Long id;

    /**
     * User id
     */
    private Long userId;

    /**
     * Assignment name
     */
    private String assignmentName;

    /**
     * Due date
     */
    private LocalDateTime dueDate;

    /**
     * Status:
     * 0 = Completed
     * 1 = Incomplete
     * 2 = Important
     */
    private Integer status;
}
