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

    public static final int CAFE_START_SEQ = 1;

    @Id
    @SequenceGenerator(name = "CAFE_START_SEQ", sequenceName = "CAFE_START_SEQ", allocationSize = 1, initialValue = CAFE_START_SEQ)
    @GeneratedValue(generator = "CAFE_START_SEQ", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 60)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", updatable = false, nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cafe_votes",
            joinColumns = {@JoinColumn(name = "cafe_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "user_id", nullable = false)})
    private Set<User> votes = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "meals", joinColumns = @JoinColumn(name = "cafe_id"))
    private List<Meal> meals = new ArrayList<>();

    public Cafe() {
    }

    public Cafe(Integer id, String name, LocalDate createdDate, List<Meal> meals) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.meals = meals;
    }

    public Cafe(Integer id, String name, LocalDate createdDate, List<Meal> meals, Set<User> votes) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.meals = meals;
        this.votes = votes;
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

//    public void setVotes(Set<User> votes) {
//        this.votes = votes;
//    }

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
                ", meals=" + meals +
                '}';
    }
}
