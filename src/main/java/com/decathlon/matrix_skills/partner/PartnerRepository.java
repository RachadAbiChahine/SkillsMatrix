package com.decathlon.matrix_skills.partner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun.security.krb5.internal.PAData;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Partner findFirstByPartnerId(Long partnerId);

    List<Partner> deleteByPartnerId(Long partnerId);


}
