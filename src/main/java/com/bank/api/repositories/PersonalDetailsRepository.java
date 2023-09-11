package com.bank.api.repositories;

import com.bank.api.models.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails,Integer> {
}
