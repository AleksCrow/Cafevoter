package com.voronkov.cafevoiter.service;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.repository.CrudCafeRepository;
import com.voronkov.cafevoiter.utils.TimeUtil;
import com.voronkov.cafevoiter.utils.exception.DontCanVoteException;
import com.voronkov.cafevoiter.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class CafeService {

    private final CrudCafeRepository cafeRepository;

    @Autowired
    public CafeService(CrudCafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public List<Cafe> getAll() {
        return cafeRepository.findAll();
    }

    public Cafe getById(int id) {
        return find(id);
    }

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public void delete(int id) {
        Cafe cafe = find(id);
        cafeRepository.delete(cafe);
    }

    public void vote(User user, int cafeId) {
        Cafe cafe = find(cafeId);
        Set<User> votes = cafe.getVotes();
        if (!TimeUtil.canVote(cafe)) {
            throw new DontCanVoteException();
        }
        if (votes.contains(user)) {
            votes.remove(user);
        } else {
            votes.add(user);
        }
        cafeRepository.save(cafe);
    }

    public List<Cafe> getByDateOrBetweenDateTimes(@Nullable LocalDate startDateTime, @Nullable LocalDate endDateTime) {
        return cafeRepository.getCafeByCreatedDateBetween(startDateTime, endDateTime);
    }

    private Cafe find(int id) {
        return cafeRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
