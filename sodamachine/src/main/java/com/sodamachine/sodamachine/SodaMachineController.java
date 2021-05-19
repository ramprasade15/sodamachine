package com.sodamachine.sodamachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class SodaMachineController {

    @Autowired
    SodaMachineService sodaMachineService;

    @GetMapping("/sodacountbysodaname/{sodaMachineId}")
    public Map<String,Soda> getAllSodasCountByMachineId(@NotNull @PathVariable String sodaMachineId) {
        return sodaMachineService.getAllSodasCountByMachineId(sodaMachineId);
    }

    @PutMapping("/soldSoda/{sodaMachineId}")
    public Soda updateSoldSodaMache(
            @NotNull @PathVariable String sodaMachineId,
            @Valid @RequestBody SodaPurchaseRequest sodaPurchaseRequest) throws SodaSoldOutException {
        return sodaMachineService.updateSodaMachine(sodaMachineId, sodaPurchaseRequest);
    }

    @PutMapping("/sodamachine/{sodaMachineId}")
    public List<Soda> updateSodas(@NotNull @PathVariable String sodaMachineId,@RequestBody List<Soda> sodaList){
        return sodaMachineService.updateSodaMachine(sodaMachineId, sodaList);
    }

    @PostMapping("/sodamachine")
    public List<Soda> addSodasToMachine(@RequestBody List<Soda> sodaList){
        return sodaMachineService.addSodasToMachine(sodaList);
    }

    @GetMapping("sodasalelist/{date}")
    public List<SodaSoldDate> getSodaSaleReport(@PathVariable String date){
        return sodaMachineService.getSodaSalesByDate(date);
    }

}
