package com.voronkov.cafevoiter.repository;

import com.voronkov.cafevoiter.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudCafeRepository extends JpaRepository<Cafe, Integer> {
//    Cafe findByName(String name);

//    @Query("select new com.voronkov.cafevoiter.to.CafeTo(" +
//            "c, " +
//            "count(cl), " +
//            "sum(case when cl =: user then 1 else 0 end) > 0" +
//            ") from Cafe c left join c.votes cl " +
//            "group by c " +
//            "order by c.id desc")
//    List<CafeTo> getAll();

//    em.createQuery("select new com.voronkov.cafevoiter.to.CafeTo" +
//            "(c, " +
//            "count(cv), " +
//            "sum(case when cv = :user then 1 else 0 end) > 0" +
//            "from Cafe c left join c.votes cv " +
//            "group by c " +
//            "order by c.id desc"
//            ).setParameter("user", user).getResultList()
}
