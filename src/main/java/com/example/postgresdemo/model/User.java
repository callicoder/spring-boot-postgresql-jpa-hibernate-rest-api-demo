package com.example.postgresdemo.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table (name = "users")
public class User extends AuditModel {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 2000
    )
    private Long id;

    @NotBlank
    @Size(min = 4, max = 2000)
    private String username;

    @Column(columnDefinition = "text")
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
