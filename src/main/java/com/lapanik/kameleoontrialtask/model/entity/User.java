package com.lapanik.kameleoontrialtask.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity {
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @NotBlank(message = "Name cannot be blank")
    @Column
    @Length(max = 30)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Column
    @Length(max = 255)
    private String password;

    @Email(message = "Email should be valid")
    @Column
    @Length(max = 100)
    private String email;

    @JsonIgnore
    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Quote> quotes = new ArrayList<>();
}
