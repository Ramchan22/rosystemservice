/**
 * RamkumarSudalaimani
 */
package com.roservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roservice.entity.User;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

	@Query(nativeQuery = true, value = "SELECT u.* FROM user u WHERE (u.user_name = :username OR u.email_id = :emailId) "
			+ "AND u.is_user_loged_in = :isUserLogedIn")
	User findByUserNameOrEmailIdAndIsUserLogedIn(String username, String emailId, boolean isUserLogedIn);

	@Query(nativeQuery = true, value = "SELECT u.* FROM user u WHERE u.user_name = :username OR u.email_id = :emailId ")
	User findByUserNameOrEmailId(String username, String emailId);

	User findByEmailId(String emailId);

	@Query("SELECT u FROM User u WHERE u.userName = :userName AND u.isUserLogedIn = :isUserLogedIn ")
	User findByUserNameAndIsUserLogedIn(String userName, boolean isUserLogedIn);

	User findByUserNameAndId(String userName, Long userId);

	@Query(nativeQuery = true, value = "SELECT u.* FROM user u ")
	List<User> findAllUserDetails();

	@Query(nativeQuery = true, value = "SELECT u.* FROM user u WHERE u.is_admin_user = 0 ")
	List<User> findAllUserDetailsExceptAdminUser();

}
