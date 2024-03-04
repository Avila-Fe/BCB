package org.example.bcb.repository;

import org.example.bcb.model.domain.ConsumoSMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumoSMSRepository extends JpaRepository<ConsumoSMS,Long> {

    List<ConsumoSMS> findByClienteIdAndDataEnvioBetween(Long clienteId, String dataInicio, String dataFim);
}
