package com.to_do_list.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
