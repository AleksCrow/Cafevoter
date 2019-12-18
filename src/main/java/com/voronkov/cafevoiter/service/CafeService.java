package com.voronkov.cafevoiter.service;

import com.voronkov.cafevoiter.model.Cafe;
import com.voronkov.cafevoiter.repository.CrudCafeRepository;
import com.voronkov.cafevoiter.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
