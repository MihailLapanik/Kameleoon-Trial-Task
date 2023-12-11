package com.lapanik.kameleoontrialtask.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Entity
@Table(name = "quote")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Quote extends BaseEntity {

    public Quote(String content, User user) {
        this.content = content;
        this.user = user;
    }

    @NotNull(message = "Content cannot be null")
    @Column
    private String content;

    @ManyToOne(cascade = PERSIST, fetch = LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(fetch = EAGER, mappedBy = "quote", cascade = ALL)
    private Set<Vote> votes = new HashSet<>();
}
