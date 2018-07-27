package com.fantasycrosscountry.fantasycrosscountry.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Runner {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String highSchool;

    @ManyToOne
    private Team team;
}
