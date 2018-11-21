package com.decathlon.matrix_skills.partner.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PartnerResponseDTO extends PartnerDTO {

    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PartnerResponseDTO)) return false;

        PartnerResponseDTO that = (PartnerResponseDTO) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }
}
