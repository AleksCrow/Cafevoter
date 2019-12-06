package com.voronkov.cafevoiter.utils;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.to.CafeTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CafeUtil {

    private CafeUtil() {
    }

    public static List<CafeTo> getCafesWithVotes(Collection<Cafe> cafes) {
        return cafes.stream().map(cafe -> createWithVote(cafe, false))
                .collect(Collectors.toList());
    }

    public static CafeTo createWithVote(Cafe cafe, boolean meVoted) {
        return new CafeTo(cafe, cafe.getVotes().size(), meVoted);
    }
}
