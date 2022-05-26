package com.kelton.clonnit.repository;

import com.kelton.clonnit.model.Subclonnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubclonnitRepository extends JpaRepository<Subclonnit, Long> {
    Optional<Subclonnit> findByName(String subclonnitName);
}
