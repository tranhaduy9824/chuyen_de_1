package com.ppl2.smartshop.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ppl2.smartshop.entities.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
	@Query("Select u from User u join fetch u.roles Where u.email=(:email) or u.phone=(:phone)")
	Optional<User> findByEmailOrPhone(@Param("email") String email,@Param("phone") String phone);
//    @Modifying
//    @Query("Update User u set u.fullname=:name,u.userPhone=:phone WHERE u.userId = (:id)")
//    void updateInfo(@Param("id") String id,
//    		@Param("name") String name,@Param("phone") String phone);
	@Query(nativeQuery = true, value = "Select * from user")
	List<User> getAllUser();
    @Modifying
    @Query("Update User u set u.password=:password WHERE u.id = (:id) ")
    int changePassword(@Param("id") Long id,@Param("password") String password);
    @Modifying
    @Query(nativeQuery = true,value= "DELETE FROM users_roles t WHERE t.user_id = (:id)")
    void deleteUserTableJoin(@Param("id") Long id);
    @Modifying
    @Query(value= "Update User u Set u.locked=(:locked)")
    void updateLock(@Param("locked") boolean locked);
    @Modifying
    @Query(value = "insert into user (user_id, user_email,fullname,password,user_phone) VALUES (:id, :email,'Admin',:pass,:phone)", nativeQuery = true)
    void insertAdmin(@Param("id") String id,@Param("email") String email,@Param("phone") String phone ,@Param("pass") String pass);
}
