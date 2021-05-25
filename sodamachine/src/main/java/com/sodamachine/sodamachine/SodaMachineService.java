package com.sodamachine.sodamachine;

import java.util.List;
import java.util.Map;

public interface SodaMachineService {
    SodaSoldReceipt updateSoldSodaMachine(String sodaMachineId, SodaPurchaseRequest sodaPurchaseRequest) throws Exception;
    List<Soda> addSodasToMachine(List<Soda> sodaList);
    Map<String, Soda> getAllSodasCountByMachineId(String machineId);
    List<Soda> updateSodaMachine(String sodaMachineId, List<Soda> sodaList);

    List<SodaSoldDate> getSodaSalesByDate(String date);

    Quarters updateQuartersToMachine(String sodaMachineId, Quarters quarters);

    List<Quarters>  addQuartersToMachine(List<Quarters> quartersList);

    Quarters getQuartersCount(String sodaMachineId);
}
