package com.thai27.chiatien.Repository;

import com.thai27.chiatien.DTO.Data.BankAccountByUserDto;
import com.thai27.chiatien.Entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(value = "select\n" +
            "\tba.id as id,\n" +
            "\tba.account_number as accountNumber,\n" +
            "\tba.qr_code_url as qrUrl,\n" +
            "\tb.bank_name as bankName,\n" +
            "\tb.image_url as bankImageUrl,\n" +
            "\tb.id as bankId\n" +
            "from\n" +
            "\tbank_account ba ,\n" +
            "\tbank b ,\n" +
            "\tchia_tien_user ctu\n" +
            "where\n" +
            "\tba.chia_tien_user_id = ctu.id\n" +
            "\tand ba.bank_id = b.id\n" +
            "\tand ctu.username = :username",nativeQuery = true)
    List<BankAccountByUserDto> findByUser(@Param("username")String username);
}
