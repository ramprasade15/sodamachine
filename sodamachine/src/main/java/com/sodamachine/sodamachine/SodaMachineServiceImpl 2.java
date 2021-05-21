package com.sodamachine.sodamachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SodaMachineServiceImpl implements SodaMachineService{

    @Autowired
    SodaRepo sodaRepo;

    @Autowired
    SodaSaleRepo sodaSaleRepo;

    @Override
    public Map<String, Soda> getAllSodasCountByMachineId(String machineId) {
        List<Soda> sodaList = sodaRepo.findBySodaMachineId(machineId);
        Map<String, Soda> result = new HashMap<>();
        for(Soda soda: sodaList){
            result.put(soda.getSodaName(), soda);
        }
        return result;
    }

    @Override
    public List<Soda> addSodasToMachine(List<Soda> sodaList) {
           return (List<Soda>) sodaRepo.saveAll(sodaList);
    }

    @Override
    public Soda updateSodaMachine(String sodaMachineId, SodaPurchaseRequest sodaPurchaseRequest) throws SodaSoldOutException {

        LocalDate date = LocalDate.now();
        Soda soda = new Soda();
        List<SodaSoldDate> sodaSoldDateList = new ArrayList<>();
        SodaSoldDate sodaSoldDate = new SodaSoldDate();
        sodaSoldDate.setSodaSoldDate(parseDate(date.toString()));
        sodaSoldDate.setSodaName(sodaPurchaseRequest.getSodaName());
        sodaSoldDate.setSodaMachineId(sodaMachineId);
        sodaSoldDateList.add(sodaSoldDate);
        Soda sodaResult  = getAllSodasCountByMachineId(sodaMachineId).get(sodaPurchaseRequest.getSodaName());
        if(sodaResult != null && sodaResult.getSodaCount() != 0 && sodaResult.getSodaName().equals(sodaPurchaseRequest.getSodaName())) {
            soda.setId(sodaResult.getId());
            soda.setSodaMachineId(sodaMachineId);
            soda.setSodaName(sodaPurchaseRequest.getSodaName());
            soda.setSodaPrice(sodaPurchaseRequest.getSodaPrice());
            soda.setSodaCount(sodaResult.getSodaCount() - 1);
            soda.setSodaSoldDateList(sodaSoldDateList);
            return sodaRepo.save(soda);
        }else {
            throw new SodaSoldOutException(sodaPurchaseRequest.getSodaName()+" is sold out. Please select other soda");
        }

    }

    private Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Soda> updateSodaMachine(String sodaMachineId, List<Soda> sodaList) {
        Map<String, Soda> sodaListMap = getAllSodasCountByMachineId(sodaMachineId);
        List<Soda> newSodaList = new ArrayList<>();
            if(!sodaListMap.isEmpty()){
                for (Soda soda : sodaList) {
                    Soda sodaMap = sodaListMap.get(soda.getSodaName());
                    soda.setId(sodaMap.getId());
                    soda.setSodaMachineId(sodaMachineId);
                    if (sodaMap.getSodaName().equals(soda.getSodaName())){
                        Soda soda1 = sodaListMap.get(soda.getSodaName());
                        soda.setSodaCount(soda1.getSodaCount() + soda.getSodaCount());
                        if (!soda1.getSodaPrice().equals(soda.getSodaPrice())) {
                            soda.setSodaPrice(soda.getSodaPrice());
                        }
                        newSodaList.add(soda);
                    }

                }
            }
            return (List<Soda>) sodaRepo.saveAll(sodaList);
        }

    @Override
    public List<SodaSoldDate> getSodaSalesByDate(String date) {

        return sodaSaleRepo.findAll();

    }
}
