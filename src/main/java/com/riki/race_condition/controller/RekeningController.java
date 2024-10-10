package com.riki.race_condition.controller;

import com.riki.race_condition.service.RekeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rekening")
public class RekeningController {

    @Autowired
    private RekeningService rekeningService;


    // API 1: Setor tanpa solusi race condition
    @PostMapping("/setorTanpaSync/{id}")
    public String setorTanpaSync(@PathVariable Long id, @RequestParam double totalSetor) {
        rekeningService.setorTanpaSync(id, totalSetor);
        return "Setor tanpa sync selesai. Saldo: " + rekeningService.checkSaldo(id);
    }

    // API 1: Tarik tanpa solusi race condition
    @PostMapping("/tarikTanpaSync/{id}")
    public String tarikTanpaSync(@PathVariable Long id, @RequestParam double totalTarik) {
        rekeningService.tarikTanpaSync(id, totalTarik);
        return "Tarik tanpa sync selesai. Saldo: " + rekeningService.checkSaldo(id);
    }

    // API 2: Setor dengan solusi synchronized (menghindari race condition)
    @PostMapping("/setorDenganSync/{id}")
    public String setorDenganSync(@PathVariable Long id, @RequestParam double totalSetor) {
        rekeningService.setorDenganSync(id, totalSetor);
        return "Setor dengan sync selesai. Saldo: " + rekeningService.checkSaldo(id);
    }

    // API 2: Tarik dengan solusi synchronized (menghindari race condition)
    @PostMapping("/tarikDenganSync/{id}")
    public String tarikDenganSync(@PathVariable Long id, @RequestParam double totalTarik) {
        rekeningService.tarikDenganSync(id, totalTarik);
        return "Tarik dengan sync selesai. Saldo: " + rekeningService.checkSaldo(id);
    }

    // API untuk mendapatkan saldo
    @GetMapping("/saldo/{id}")
    public String checkSaldo(@PathVariable Long id) {
        return "Saldo: " + rekeningService.checkSaldo(id);
    }
}
