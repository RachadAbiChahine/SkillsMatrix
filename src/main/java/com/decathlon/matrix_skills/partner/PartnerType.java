package com.decathlon.matrix_skills.partner;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "partner_type")
public class PartnerType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "partner_type_id")
    int PartnerTypeId;
    @Column(name = "type")

    @Enumerated(EnumType.STRING)
    EnumPartnerType partnerType;


    public int getPartnerTypeId() {
        return PartnerTypeId;
    }

    public void setPartnerTypeId(int partnerTypeId) {
        PartnerTypeId = partnerTypeId;
    }

    public EnumPartnerType getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(EnumPartnerType partnerType) {
        this.partnerType = partnerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PartnerType)) return false;

        PartnerType that = (PartnerType) o;

        return new EqualsBuilder()
                .append(getPartnerTypeId(), that.getPartnerTypeId())
                .append(getPartnerType(), that.getPartnerType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPartnerTypeId())
                .append(getPartnerType())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PartnerType{" +
                "PartnerTypeId=" + PartnerTypeId +
                ", partnerType=" + partnerType +
                '}';
    }
}