package kr.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import kr.spring.entity.Member;


public interface MemberRepository extends JpaRepository<Member, String>{
	
	
}
