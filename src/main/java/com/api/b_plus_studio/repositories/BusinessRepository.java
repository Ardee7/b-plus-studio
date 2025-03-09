package com.api.b_plus_studio.repositories;

import com.api.b_plus_studio.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

}
