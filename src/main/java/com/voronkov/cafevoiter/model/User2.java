package com.voronkov.cafevoiter.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User2 {

    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(generator = "USER_SEQ", strategy = GenerationType.SEQUENCE)
    protected int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "isVote")
    private boolean isVote;

    public User2() {
    }

    public User2(int id, String email, String name, String password, Set<Role> roles, boolean isVote) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        setRoles(roles);
        this.isVote = isVote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isVote() {
        return isVote;
    }

    public void setVote(boolean vote) {
        isVote = vote;
    }
}
