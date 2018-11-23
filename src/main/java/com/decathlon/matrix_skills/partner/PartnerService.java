package com.decathlon.matrix_skills.partner;

import com.decathlon.matrix_skills.partner.dto.PartnerDTO;
import com.decathlon.matrix_skills.partner.dto.PartnerResponseDTO;
import com.decathlon.matrix_skills.partner.errors.PartnerNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    PartnerRepository partnerRepository;

    ModelMapper modelMapper;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, ModelMapper modelMapper) {
        this.partnerRepository = partnerRepository;
        this.modelMapper = modelMapper;
    }

    public PartnerResponseDTO addPartner(PartnerDTO partnerRequestDto) {
        Partner partner = partnerRepository.save(modelMapper.map(partnerRequestDto, Partner.class));
        return modelMapper.map(partner, PartnerResponseDTO.class);
    }

    @Transactional
    public PartnerResponseDTO editPartner(Long partnerId, PartnerDTO partnerDTO) throws PartnerNotFoundException {
        Partner partner = partnerRepository.findFirstByPartnerId(partnerId);
        if (partner == null) {
            throw new PartnerNotFoundException();
        } else {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(partnerDTO, partner);
            return modelMapper.map(partnerRepository.saveAndFlush(partner), PartnerResponseDTO.class);
        }
    }

    @Transactional
    public PartnerResponseDTO deletePartner(Long partnerId) throws PartnerNotFoundException {
        List<Partner> partners = partnerRepository.deleteByPartnerId(partnerId);
        if (partners == null || partners.isEmpty()) {
            throw new PartnerNotFoundException();
        }

        return modelMapper.map(partners.get(0), PartnerResponseDTO.class);
    }

    @Transactional
    public PartnerResponseDTO getPartner(Long partnerId) throws PartnerNotFoundException {
        Partner partner = partnerRepository.findFirstByPartnerId(partnerId);
        if (partner == null) {
            throw new PartnerNotFoundException();
        }
        return modelMapper.map(partner, PartnerResponseDTO.class);
    }

    @Transactional
    public List<PartnerResponseDTO> getAllPartners() throws PartnerNotFoundException {
        List<Partner> partners = partnerRepository.findAll();
        if (partners == null || partners.isEmpty()) {
            throw new PartnerNotFoundException();
        }
        return partners.stream().map(entity -> modelMapper.map(entity, PartnerResponseDTO.class)).collect(Collectors.toList());
    }
}
