package com.thai27.chiatien.Repository;

import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.Entity.ChiaTienUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Page<Bank> findAll(Pageable pageable);

    Page<Bank> findAllByBankNameLikeIgnoreCase(String bankName, PageRequest pageRequest);

    Optional<Bank> findByBankName(String bankName);
}
