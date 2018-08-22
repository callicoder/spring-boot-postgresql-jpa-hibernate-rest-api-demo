package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Member;
import com.example.postgresdemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member")
    public Page<Member> getMember(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }


    @PostMapping("/member")
    public Member createMember(@Valid @RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/member/{memberId}")
    public Member updateMember(@PathVariable Long memberId,
                             @Valid @RequestBody Member memberRequest) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    member.setName(memberRequest.getName());
                    return memberRepository.save(member);
                }).orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + memberId));
    }


    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    memberRepository.delete(member);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + memberId));
    }
}
