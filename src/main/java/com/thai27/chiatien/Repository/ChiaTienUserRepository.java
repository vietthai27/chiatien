package com.thai27.chiatien.Repository;

import com.thai27.chiatien.DTO.Data.BankAccountByUserDto;
import com.thai27.chiatien.DTO.Data.UserListDto;
import com.thai27.chiatien.Entity.ChiaTienUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiaTienUserRepository extends JpaRepository<ChiaTienUser, Long> {

    Optional<ChiaTienUser> findByUsername(String username);

    Optional<ChiaTienUser> findByEmail(String username);

    boolean existsByUsername(String username);

    @Query(value = "select * from chia_tien_user where id in (:listId) and username <> 'ADMIN'",nativeQuery = true)
    List<ChiaTienUser> findUserByListId(@Param("listId") List<Long> listId);

    @Query("SELECT u FROM ChiaTienUser u WHERE LOWER(u.username) LIKE LOWER(:username) AND u.username <> 'ADMIN'")
    Page<ChiaTienUser> findAllByUsernameLikeIgnoreCaseExcludingAdmin(@Param("username") String username, Pageable pageable);

    @Query("SELECT u FROM ChiaTienUser u WHERE u.username <> 'ADMIN'")
    Page<ChiaTienUser> findAllExcludingAdmin(Pageable pageable);

    @Query("SELECT u FROM ChiaTienUser u WHERE u.username <> 'ADMIN'")
    List<ChiaTienUser> findAllExcludingAdminList();
}
