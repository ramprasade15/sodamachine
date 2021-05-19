package com.sodamachine.sodamachine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SodaRepo extends CrudRepository<Soda, Integer> {

    List<Soda> findBySodaMachineId(String machineId);
    Soda save (Soda soda);
    //List<Soda> saveAll(List<Soda> sodaList);
}
