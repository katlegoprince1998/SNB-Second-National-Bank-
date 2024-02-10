package com.codeword.snb.entity.userAddress;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "residentialAddress",
                column = @Column(name = "residential_address"))
})
public class Address {
    private String residentialAddress;
    private String city;
    private String province;
 }
