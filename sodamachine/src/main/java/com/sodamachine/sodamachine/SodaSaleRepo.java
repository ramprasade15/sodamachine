package com.sodamachine.sodamachine;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SodaSaleRepo extends PagingAndSortingRepository<SodaSoldDate, Integer> {
List<SodaSoldDate> findAll();
}
