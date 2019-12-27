package com.voronkov.cafevoiter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cafes")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Cafe {

    private static final int START_SEQ = 1;

    @Id
    @SequenceGenerator(name = "CAFE_SEQ", sequenceName = "CAFE_SEQ", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(generator = "CAFE_SEQ", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 60)
    @Column(name = "name")
    private String name;

    @Column(name = "date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cafe_votes",
            joinColumns = {@JoinColumn(name = "cafe_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> votes = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "meals", joinColumns = @JoinColumn(name = "cafe_id"))
    private List<Meal> meals = new ArrayList<>();

    public Cafe() {
    }

    public Cafe(String name, List<Meal> meals) {
        this.name = name;
        this.createdDate = LocalDate.now();
        this.meals = meals;
    }

    public Integer getId() {
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<User> getVotes() {
        return votes;
    }

    public void setVotes(Set<User> votes) {
        this.votes = votes;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Cafe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
