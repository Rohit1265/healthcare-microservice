package com.centene.healthcare.repository;

import com.centene.healthcare.domain.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {

    Optional<Enrollee> findById(long id);
}
