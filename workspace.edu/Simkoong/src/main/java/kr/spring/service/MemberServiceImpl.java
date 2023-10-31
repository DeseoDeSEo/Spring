package kr.spring.service;


import org.springframework.beans.factory.annotation.Autowired;

import kr.spring.entity.Member;
import kr.spring.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public void join(Member vo) {
		memberRepository.save(vo);
	}
	
}
