package com.voronkov.cafevoiter.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.voronkov.cafevoiter.model.Cafe;

import java.time.LocalDate;
import java.util.Objects;

public class CafeTo {

    private Integer id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private Integer votes;

    public CafeTo() {
    }

    public CafeTo(Cafe cafe, Integer votes) {
        this.id = cafe.getId();
        this.name = cafe.getName();
        this.createdDate = cafe.getCreatedDate();
        this.votes = votes;
    }

    public CafeTo(Integer id, String name, LocalDate createdDate, Integer votes) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Integer getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "CafeTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", votes=" + votes +
                "}";
    }

    //без equals не проходит валидацию тест CafeRestController.getAllCafes()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CafeTo)) return false;
        CafeTo cafeTo = (CafeTo) o;
        return Objects.equals(id, cafeTo.id) &&
                Objects.equals(name, cafeTo.name) &&
                Objects.equals(createdDate, cafeTo.createdDate) &&
                Objects.equals(votes, cafeTo.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdDate, votes);
    }
}
