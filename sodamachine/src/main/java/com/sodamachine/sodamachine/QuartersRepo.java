package com.sodamachine.sodamachine;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@ResponseBody
@Transactional
public interface QuartersRepo extends PagingAndSortingRepository<Quarters,Integer> {
    Quarters findBySodaMachineId(String machineId);
    Quarters save(Quarters quarters);
}
