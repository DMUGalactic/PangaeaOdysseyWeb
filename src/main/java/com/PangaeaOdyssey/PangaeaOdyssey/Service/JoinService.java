package com.PangaeaOdyssey.PangaeaOdyssey.Service;

import com.PangaeaOdyssey.PangaeaOdyssey.Authority;
import com.PangaeaOdyssey.PangaeaOdyssey.DTO.JoinDTO;
import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import com.PangaeaOdyssey.PangaeaOdyssey.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        String id = joinDTO.getId();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String filename = joinDTO.getFilename();
        String auth = joinDTO.getRole();
        System.out.println("현재: "+auth);

        Boolean isExist = memberRepository.existsById(id);

        if (isExist) {
            return;
        }

        Member data = new Member();

        data.setId(id);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setEmail(email);
        data.setFilename(filename);
        data.setRole(auth);

        memberRepository.save(data);
    }
}
