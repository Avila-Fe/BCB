package org.example.bcb.repository;

import org.example.bcb.model.domain.SMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSRepository extends JpaRepository<SMS,Long> {

}
