package com.centene.healthcare.repository;

import com.centene.healthcare.domain.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {

    Optional<Dependent> findById(long id);
}
