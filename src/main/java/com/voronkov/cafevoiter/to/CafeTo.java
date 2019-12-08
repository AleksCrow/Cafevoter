package com.voronkov.cafevoiter.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.voronkov.cafevoiter.model.Cafe;

import java.time.LocalDateTime;

public class CafeTo {

    private Integer id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private Integer votes;

    public CafeTo(Cafe cafe, Integer votes) {
        this.id = cafe.getId();
        this.name = cafe.getName();
        this.createdDate = cafe.getCreatedDate();
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
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
                '}';
    }
}
