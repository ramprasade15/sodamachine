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

    @Autowired
    QuartersRepo quartersRepo;

    @Override
    public Map<String, Soda> getAllSodasCountByMachineId(String machineId) {
        List<Soda> sodaList = sodaRepo.findBySodaMachineId(machineId);
        Map<String, Soda> result = new HashMap<>();
        for(Soda soda: sodaList){
            result.put(soda.getSodaName(), soda);
        }
        return result;
    }

    public Soda getSodaByName(String sodaName) {
        Soda soda = sodaRepo.findBySodaName(sodaName);

        return soda;
    }

    @Override
    public List<Soda> addSodasToMachine(List<Soda> sodaList) {
        Map<String, Soda> sodaListInMachine = getAllSodasCountByMachineId(sodaList.get(0).getSodaMachineId());
        List<Soda>  updateSodaList = new ArrayList<>();
        for(Soda soda: sodaList){
            Soda sodaInMachine = sodaListInMachine.get(soda.getSodaName());
            if(sodaInMachine != null && sodaInMachine.getSodaName().equals(sodaInMachine.getSodaName())){
                updateSodaList.add(soda);
            }
        }
            if(updateSodaList.size() >0){
                return updateSodaMachine(sodaList.get(0).getSodaMachineId(), updateSodaList);
            }else {
                return (List<Soda>) sodaRepo.saveAll(sodaList);
            }
    }

    @Override
    public SodaSoldReceipt updateSoldSodaMachine(String sodaMachineId, SodaPurchaseRequest sodaPurchaseRequest) throws Exception {
        SodaSoldReceipt sodaSoldReceipt = new SodaSoldReceipt();
        Soda sodaResult = getSodaByName(sodaPurchaseRequest.getSodaName());
        if(sodaResult != null && sodaResult.getSodaPrice() <= sodaPurchaseRequest.getMoneyInserted()) {
            LocalDate date = LocalDate.now();
            Date soldDate = parseDate(date.toString());
            Soda soda = new Soda();
            List<SodaSoldDate> sodaSoldDateList = new ArrayList<>();
            SodaSoldDate sodaSoldDate = new SodaSoldDate();
            sodaSoldDate.setSodaSoldDate(soldDate);
            sodaSoldDate.setSodaName(sodaPurchaseRequest.getSodaName());
            sodaSoldDate.setSodaMachineId(sodaMachineId);
            sodaSoldDateList.add(sodaSoldDate);

            Integer noOfQuarters = quartersRepo.findBySodaMachineId(sodaMachineId).getQuartersCount();
            Double countNoOfQuartersToReturn = ((sodaPurchaseRequest.getMoneyInserted() - sodaResult.getSodaPrice()) / 0.25);
            Integer noOfQuartersToReturn = countNoOfQuartersToReturn.intValue();
            if (noOfQuartersToReturn < noOfQuarters && sodaResult != null && sodaResult.getSodaCount() != 0 &&
                    sodaResult.getSodaName().equals(sodaPurchaseRequest.getSodaName())) {
                soda.setId(sodaResult.getId());
                soda.setSodaMachineId(sodaMachineId);
                soda.setSodaName(sodaPurchaseRequest.getSodaName());
                soda.setSodaPrice(sodaResult.getSodaPrice());
                soda.setSodaCount(sodaResult.getSodaCount() - 1);
                soda.setSodaSoldDateList(sodaSoldDateList);
                Soda soldSoda = sodaRepo.save(soda);
                if(soldSoda != null){
                    sodaSoldReceipt.setSodaMachineId(soldSoda.getSodaMachineId());
                    sodaSoldReceipt.setTransactionId(soldSoda.getSodaSoldDateList().get(0).getId().toString());
                    sodaSoldReceipt.setTransactionDate(date.toString());
                    sodaSoldReceipt.setSodaName(soldSoda.getSodaName());
                    sodaSoldReceipt.setSodaPrice(soldSoda.getSodaPrice());
                    sodaSoldReceipt.setInsertedAmount(sodaPurchaseRequest.getMoneyInserted());
                    sodaSoldReceipt.setReturnQuarters(noOfQuartersToReturn);
                }
                return  sodaSoldReceipt;
            } else {
                if (noOfQuartersToReturn > noOfQuarters) {
                    throw new OutOfQuartersException("We don't have sufficient Quarters.");
                } else {
                    throw new SodaSoldOutException(sodaPurchaseRequest.getSodaName() + " is sold out. Please select other soda");
                }
            }
        }else
            if(sodaResult == null){
                throw new SodaSoldOutException(sodaPurchaseRequest.getSodaName() + " is sold out. Please select other soda");
            }else {
                throw new Exception("Please insert remaining $" + (sodaResult.getSodaPrice() - sodaPurchaseRequest.getMoneyInserted()) + " Dollars");
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
                        if (soda1.getSodaPrice() == null || !soda1.getSodaPrice().equals(soda.getSodaPrice())) {
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

    @Override
    public Quarters updateQuartersToMachine(String sodaMachineId, Quarters quarters) {
        Integer noOfQuarters = quartersRepo.findBySodaMachineId(sodaMachineId).getQuartersCount();
        quarters.setQuartersCount(noOfQuarters+quarters.getQuartersCount());
        return quartersRepo.save(quarters);
    }

    @Override
    public List<Quarters> addQuartersToMachine(List<Quarters> quartersList) {
        return (List<Quarters>) quartersRepo.saveAll(quartersList);
    }

}
