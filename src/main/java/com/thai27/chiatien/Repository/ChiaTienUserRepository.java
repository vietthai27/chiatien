package com.thai27.chiatien.Repository;

import com.thai27.chiatien.Entity.ChiaTienUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiaTienUserRepository extends JpaRepository<ChiaTienUser, Long> {
}
