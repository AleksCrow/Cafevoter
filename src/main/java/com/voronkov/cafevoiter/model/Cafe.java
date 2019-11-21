package com.voronkov.cafevoiter.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cafes")
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

    @Column(name = "rating")
    private int rating;

    @Column(name = "date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    public Cafe() {
    }

    public Cafe(@NotBlank @Size(min = 1, max = 60) String name, int rating) {
        this.name = name;
        this.rating = rating;
        this.createdDate = LocalDateTime.now();
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

}
