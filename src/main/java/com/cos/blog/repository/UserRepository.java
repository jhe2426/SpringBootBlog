package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.domain.User;

//DAO
// 자동으로 bean등록이 된다
//@Repository 생략 가능 자동으로 bean등록이 되므로
public interface UserRepository extends JpaRepository<User, Long> {

}
