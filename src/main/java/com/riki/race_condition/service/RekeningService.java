package com.riki.race_condition.service;

import com.riki.race_condition.model.Rekening;
import com.riki.race_condition.repository.RekeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RekeningService {

    @Autowired
    private RekeningRepository rekeningRepository;


    // API 1: Simulasi tanpa solusi (Race Condition)
    public void setorTanpaSync(Long rekeningId, double totalSetor) {
        Optional<Rekening> rekeningOptional = rekeningRepository.findById(rekeningId);
        if (rekeningOptional.isPresent()) {
            Rekening rekening = rekeningOptional.get();
            double saldoSekarang = rekening.getSaldo();
            rekening.setSaldo(saldoSekarang + totalSetor); // Menambah saldo
            rekeningRepository.save(rekening);
        }
    }

    public void tarikTanpaSync(Long rekeningId, double totalTarik) {
        Optional<Rekening> rekeningOptional = rekeningRepository.findById(rekeningId);
        if (rekeningOptional.isPresent()) {
            Rekening rekening = rekeningOptional.get();
            double saldoSekarang = rekening.getSaldo();
            rekening.setSaldo(saldoSekarang - totalTarik); // Mengurangi saldo
            rekeningRepository.save(rekening);
        }
    }

    // API 2: Solusi dengan synchronized untuk mengatasi Race Condition
    public synchronized void setorDenganSync(Long rekeningId, double totalSetor) {
        Optional<Rekening> rekeningOptional = rekeningRepository.findById(rekeningId);
        if (rekeningOptional.isPresent()) {
            Rekening rekening = rekeningOptional.get();
            double saldoSekarang = rekening.getSaldo();
            rekening.setSaldo(saldoSekarang + totalSetor); // Menambah saldo
            rekeningRepository.save(rekening);
        }
    }

    public synchronized void tarikDenganSync(Long rekeningId, double totalTarik) {
        Optional<Rekening> rekeningOptional = rekeningRepository.findById(rekeningId);
        if (rekeningOptional.isPresent()) {
            Rekening rekening = rekeningOptional.get();
            double saldoSekarang = rekening.getSaldo();
            rekening.setSaldo(saldoSekarang - totalTarik); // Mengurangi saldo
            rekeningRepository.save(rekening);
        }
    }

    // Check saldo untuk debugging
    public double checkSaldo(Long rekeningId) {
        Optional<Rekening> rekeningOptional = rekeningRepository.findById(rekeningId);
        return rekeningOptional.map(Rekening::getSaldo).orElse(0.0);
    }

}
