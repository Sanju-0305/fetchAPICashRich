package com.coinmarketcap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.coinmarketcap.dto.UserDetailDTO;
import com.coinmarketcap.entity.User;


@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long>{

	@Query(value = "SELECT count(*) FROM user_details where email = :email",nativeQuery=true)
	int findbyEmail(@Param("email") String email);
	
	@Query(value = "SELECT count(*) FROM user_details where user_name = :userName",nativeQuery=true)
	int findbyUserName(@Param("userName") String userName);

	@Query(value = "SELECT * FROM user_details where user_name = :userName",nativeQuery=true)
	User findFirstByEmail(@Param("userName") String email);

	 @Query(value = "SELECT * FROM user_details where email = :email",nativeQuery=true)
	UserDetailDTO findbyEmail1(String email);

	@Query(value = "SELECT * FROM user_details where user_name = :userName",nativeQuery=true)
	User loadUserByUsername(String userName);
	
	@Query(value = "SELECT * FROM user_details where user_name = :userName",nativeQuery=true)
	UserDetails loadUserByUsername1(String userName);

	
	
}
