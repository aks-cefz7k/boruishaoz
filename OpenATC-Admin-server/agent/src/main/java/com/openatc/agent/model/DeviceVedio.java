package com.openatc.agent.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "devs_video")
@Entity
public class DeviceVedio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agentid;

    private String name;

    private String url;
}
