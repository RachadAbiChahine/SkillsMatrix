package com.decathlon.matrix_skills.partner;

import com.decathlon.matrix_skills.common.Constants;
import com.decathlon.matrix_skills.partner.dto.PartnerDTO;
import com.decathlon.matrix_skills.partner.dto.PartnerResponseDTO;
import com.decathlon.matrix_skills.partner.errors.PartnerNotFoundException;
import com.decathlon.matrix_skills.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_PATH + "/partner")
@Validated
public class PartnerResource {


    private PartnerService partnerService;

    @Autowired
    public PartnerResource(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("/")
    public ResponseEntity<PartnerResponseDTO> addPartner(@Valid
                                                         @RequestBody PartnerDTO partnerRequestDto) {
        PartnerResponseDTO partnerResponseDTO = partnerService.addPartner(partnerRequestDto);
        return ResponseEntity.ok(partnerResponseDTO);
    }

    @PatchMapping("/{partnerId}")
    public ResponseEntity<PartnerResponseDTO> editPartner(@DecimalMin("0")
                                                          @PathVariable("partnerId") Long partnerId,
                                                          @Valid
                                                          @RequestBody PartnerDTO partnerDTO) throws PartnerNotFoundException {
        return ResponseEntity.ok(partnerService.editPartner(partnerId, partnerDTO));
    }


    @DeleteMapping("/{partnerId}")

    public ResponseEntity<PartnerResponseDTO> deletePartner(@DecimalMin("0")
                                                            @PathVariable("partnerId") Long partnerId) throws PartnerNotFoundException {
        return ResponseEntity.ok(partnerService.deletePartner(partnerId));

    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerResponseDTO> findPartner(@DecimalMin("0")
                                                             @PathVariable("partnerId") Long partnerId) throws PartnerNotFoundException {
        return ResponseEntity.ok(partnerService.getPartner(partnerId));
    }

    @GetMapping("/")
    public ResponseEntity<List<PartnerResponseDTO>>  findAllPartners(){
        return ResponseEntity.ok(partnerService.getAllPartners());
    }
}
