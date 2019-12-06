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
    private Boolean meVoted;

    public CafeTo(Cafe cafe, Integer votes, Boolean meVoted) {
        this.id = cafe.getId();
        this.name = cafe.getName();
        this.createdDate = cafe.getCreatedDate();
        this.votes = votes;
        this.meVoted = meVoted;
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

    public Boolean getMeVoted() {
        return meVoted;
    }

    @Override
    public String toString() {
        return "CafeTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", votes=" + votes +
                ", meVoted=" + meVoted +
                '}';
    }
}
