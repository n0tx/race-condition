package com.riki.race_condition;

import com.riki.race_condition.model.Rekening;
import com.riki.race_condition.repository.RekeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RaceConditionApplication  implements CommandLineRunner {

	@Autowired
	RekeningRepository rekeningRepository;

	public static void main(String[] args) {
		SpringApplication.run(RaceConditionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (rekeningRepository.count() == 0) {
			// Membuat rekening dan menambahkan saldo
			Rekening rekening = rekeningRepository.save(new Rekening(1L, 1000));
			System.out.println("Initial data rekening baru berhasil dibuat");
			System.out.println("rekening ID: " + rekening.getId());
			System.out.println("Saldo: " + rekening.getSaldo());
		} else {
			System.out.println("Data sudah ada di tabel rekening");
		}

	}
}
