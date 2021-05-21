package com.sodamachine.sodamachine;

import java.util.List;
import java.util.Map;

public interface SodaMachineService {
    Soda updateSodaMachine(String sodaMachineId, SodaPurchaseRequest sodaPurchaseRequest) throws SodaSoldOutException;
    List<Soda> addSodasToMachine(List<Soda> sodaList);
    Map<String, Soda> getAllSodasCountByMachineId(String machineId);
    List<Soda> updateSodaMachine(String sodaMachineId, List<Soda> sodaList);

    List<SodaSoldDate> getSodaSalesByDate(String date);
}
