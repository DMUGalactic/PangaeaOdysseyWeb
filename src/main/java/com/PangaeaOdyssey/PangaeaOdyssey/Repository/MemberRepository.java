package com.PangaeaOdyssey.PangaeaOdyssey.Repository;

import com.PangaeaOdyssey.PangaeaOdyssey.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsById(String id);
}
