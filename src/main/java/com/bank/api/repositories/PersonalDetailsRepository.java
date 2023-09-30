package com.bank.api.repositories;

import com.bank.api.entity.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails,String> {
    Optional<PersonalDetails> findByAccountNumber(int accountNumber);
    Optional<PersonalDetails> findByEmail(String email);
    Optional<PersonalDetails> findByContactNumber(String username);

    Optional<PersonalDetails> findByIdentityProofNumber(String username);

    Optional<PersonalDetails> findByUsername(String username);
    List<PersonalDetails> findAllByAccepted(Boolean accepted);

}
