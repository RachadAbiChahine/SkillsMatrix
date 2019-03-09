package com.matrix_skills.partner;

import com.matrix_skills.Application;
import com.matrix_skills.partner.dto.PartnerDTO;
import com.matrix_skills.partner.dto.PartnerResponseDTO;
import com.matrix_skills.partner.errors.PartnerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PartnerServiceTest {

    public static final String NAME = "BOB";
    public static final String SURNAME = "Chalie";
    @Autowired
    PartnerService partnerService;

    @Autowired
    PartnerRepository partnerRepository;

    PartnerDTO partnerRequest;

    @BeforeEach
    void setUp() {
        partnerRequest = new PartnerDTO();
        partnerRequest.setName(NAME);
        partnerRequest.setSurname(SURNAME);
    }

    @Test
    @DisplayName("adds partner test")
    void addPartner() {
        PartnerResponseDTO partnerResponseDTO = partnerService.addPartner(partnerRequest);
        assertEquals(partnerRequest.getName(), partnerResponseDTO.getName());
        assertEquals(Long.valueOf(1L).intValue(), partnerResponseDTO.getId().intValue());
    }

    @Test
    @DisplayName("edit existing Partner")
    void editPartner() throws PartnerNotFoundException {
        Partner partner = new Partner();
        partner.setSurname(SURNAME);
        ;
        partner.setName(NAME);
        Partner savedPartner = partnerRepository.save(partner);
        PartnerDTO mofyingPartner = new PartnerDTO();
        mofyingPartner.setSurname("ANOTHER");
        PartnerResponseDTO partnerResponseDTO = partnerService.editPartner(savedPartner.getPartnerId(), mofyingPartner);
        assertEquals(savedPartner.getPartnerId(), partnerResponseDTO.getId());
        assertEquals(mofyingPartner.getSurname(), partnerResponseDTO.getSurname());
    }

    @Test
    @DisplayName("edit non existingpartner")
    void editPartner_editNonFoundPartner() throws PartnerNotFoundException {
        assertThrows(PartnerNotFoundException.class, () -> partnerService.editPartner(244L, partnerRequest));
    }

    @Test
    @DisplayName("try to delete existed partner")
    void deletePartner() throws PartnerNotFoundException {
        Partner partner = new Partner();
        partner.setSurname(SURNAME);
        partner.setName(NAME);
        Partner savedPartner = partnerRepository.save(partner);
        PartnerResponseDTO partnerResponseDTO = partnerService.deletePartner(savedPartner.getPartnerId());
        assertEquals(savedPartner.getPartnerId(), partnerResponseDTO.getId());
        assertEquals(savedPartner.getSurname(), partnerResponseDTO.getSurname());
    }

    @Test
    @DisplayName("try to delete existed partner")
    void deletePartner_NonExistingPartner() throws PartnerNotFoundException {
        assertThrows(PartnerNotFoundException.class, () -> partnerService.deletePartner(200L));
    }

    @Test
    @DisplayName("get Existing partner")
    void getPartner() throws PartnerNotFoundException {
        Partner partner = new Partner();
        partner.setSurname(SURNAME);
        partner.setName(NAME);
        Partner savedPartner = partnerRepository.save(partner);
        PartnerResponseDTO partnerResponseDTO = partnerService.getPartner(savedPartner.getPartnerId());
        assertTrue(partnerResponseDTO.getId() == savedPartner.getPartnerId());
        assertEquals(savedPartner.getSurname(), partnerResponseDTO.getSurname());
        assertEquals(savedPartner.getEnumPartnerType(), partnerResponseDTO.getEnumPartnerType());
        assertEquals(savedPartner.getName(), partnerResponseDTO.getName());
    }

    @Test
    @DisplayName("get non Existing partner")
    void getPartner_nonExistingPartner() throws PartnerNotFoundException {
        assertThrows(PartnerNotFoundException.class, () -> partnerService.getPartner(200L));
    }

    @Test
    void getAllPartners() throws PartnerNotFoundException {
        Partner partner = new Partner();
        partner.setSurname(SURNAME);
        partner.setName(NAME);
        Partner savedPartner = partnerRepository.save(partner);
        List<PartnerResponseDTO> partnerResponseDTOS = partnerService.getAllPartners();
        assertTrue(partnerResponseDTOS.size() == 1);
        assertEquals(partnerResponseDTOS.get(0).getId(), savedPartner.getPartnerId());
        assertEquals(partnerResponseDTOS.get(0).getName(), savedPartner.getName());
        assertEquals(partnerResponseDTOS.get(0).getName(), savedPartner.getName());
    }


    @Test
    void getAllPartners_NonExistingPartners() throws PartnerNotFoundException {
        assertThrows(PartnerNotFoundException.class, () -> partnerService.getAllPartners());
    }
}

