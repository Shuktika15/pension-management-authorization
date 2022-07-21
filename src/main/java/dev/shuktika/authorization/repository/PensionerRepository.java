package dev.shuktika.authorization.repository;

import dev.shuktika.authorization.entity.Pensioner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PensionerRepository extends JpaRepository<Pensioner, Long> {
    Optional<Pensioner> findById(Long aadharNumber);
}
