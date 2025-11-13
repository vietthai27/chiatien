package com.thai27.chiatien.Repository;

import com.thai27.chiatien.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


	Optional<Role> findByRolename(String userRole);

	@Query(value = "INSERT INTO chia_tien_user_role(\n" +
			"\t chia_tien_user_id, role_id)\n" +
			"\t VALUES (:userId, (select id from role where role_name = 'MODER'))",nativeQuery = true)
	@Modifying
	@Transactional
	void setUserModerRole(@Param("userId") Long userId);

	@Query(value = "delete from chia_tien_user_role where chia_tien_user_id = :userId and role_id = (select id from role where role_name = 'MODER')",nativeQuery = true)
	@Transactional
	@Modifying
	void unsetUserModerRole(@Param("userId")Long userId);

	@Query(value = "delete from chia_tien_user_role where chia_tien_user_id = :userId",nativeQuery = true)
	@Transactional
	@Modifying
	int deleteAllUserRoles(@Param("userId")Long userId);
}
