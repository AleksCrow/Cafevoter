package com.voronkov.cafevoiter.service;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.model.User;
import com.voronkov.cafevoiter.repository.CrudCafeRepository;
import com.voronkov.cafevoiter.utils.TimeUtil;
import com.voronkov.cafevoiter.utils.exception.DontCanVoteException;
import com.voronkov.cafevoiter.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class CafeService {

    private static Logger log = LoggerFactory.getLogger(CafeService.class);

    private final CrudCafeRepository cafeRepository;

    @Autowired
    public CafeService(CrudCafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public List<Cafe> getAll() {
        log.info("IN найдено: {} кафешек", cafeRepository.findAll().size());
        return cafeRepository.findAll();
    }

    public Cafe getById(int id) {
        Cafe cafe = cafeRepository.findById(id).orElse(null);
        if (cafe == null) {
            log.info("IN кафе с id: {} не найдено", id);
            throw new NotFoundException();
        }
        log.info("IN кафе с id: {} найдено", id);
        return cafe;
    }

    public Cafe save(Cafe cafe) {
        log.info("IN кафе {} сохранено", cafe.getName());
        log.info("IN его блюда: {}", cafe.getMeals().toString());
        return cafeRepository.save(cafe);
    }

    public void delete(Cafe cafe) {
        log.info("IN кафе {} удалено", cafe.getName());
        cafeRepository.delete(cafe);
    }

    public List<Cafe> vote(User user, Cafe cafe) {
        Set<User> votes = cafe.getVotes();
        if (!TimeUtil.canVote(cafe)) {
            log.info("IN время для голосования окончено");
            throw new DontCanVoteException();
        }
        if (votes.contains(user)) {
            log.info("IN кафе {} получает: -1 голос", cafe.getName());
            votes.remove(user);
        } else {
            log.info("IN кафе {} получает: +1 голос", cafe.getName());
            votes.add(user);
        }
        log.info("IN у кафе {} - {} голос", cafe.getName(), votes.size());
        cafeRepository.save(cafe);
        return cafeRepository.findAll();
    }

    public List<Cafe> getByDateOrBetweenDateTimes(@Nullable LocalDate startDateTime, @Nullable LocalDate endDateTime) {
        log.info("IN отфильтрованы: {}", cafeRepository.getCafeByCreatedDateBetween(LocalDate.of(2019, 11, 10), LocalDate.of(2019, 11, 10)));
        return cafeRepository.getCafeByCreatedDateBetween(startDateTime, endDateTime);
    }
}
