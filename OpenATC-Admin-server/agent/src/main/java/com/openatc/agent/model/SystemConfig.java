package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "systemconfig")
public class SystemConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String module;

    private String key;

    private String value;

    private String description;

    private Boolean isValid = true;
}
