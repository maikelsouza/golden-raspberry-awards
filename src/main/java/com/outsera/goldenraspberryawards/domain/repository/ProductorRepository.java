package com.outsera.goldenraspberryawards.domain.repository;

import com.outsera.goldenraspberryawards.api.dto.ProducerAndYearDto;
import com.outsera.goldenraspberryawards.domain.model.Productor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductorRepository extends JpaRepository<Productor, Long> {


    Set<Productor> findByNameIn(String[] names);

    @Query("select p.id from Productor p join p.movies m where m.winner = true group by p having count(m) > 1")
    List<Long> findIdProducerWithWinningMovies();

    @Query("select p.name as name, m.year as year from Productor p join p.movies m where p.id in :ids and m.winner = true group by p.name, m.year order by p.name, m.year")
    List<ProducerAndYearDto> findProducersAndYearsWithWinningMoviesByIDs(@Param("ids") List<Long> ids);
}
