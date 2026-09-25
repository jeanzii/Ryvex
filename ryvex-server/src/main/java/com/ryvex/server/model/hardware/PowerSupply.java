package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.PsuEfficiencyRating;
import com.ryvex.server.model.hardware.enumtype.PsuFormFactor;
import com.ryvex.server.model.hardware.enumtype.PsuModularity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "power_supplies"
)
public class PowerSupply extends HardwareComponent {

    @Column(
            name = "wattage",
            nullable = false
    )
    private int wattage;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "form_factor",
            nullable = false,
            length = 20
    )
    private PsuFormFactor formFactor;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "efficiency_rating",
            nullable = false,
            length = 20
    )
    private PsuEfficiencyRating efficiencyRating;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "modularity",
            nullable = false,
            length = 30
    )
    private PsuModularity modularity;

    protected PowerSupply() {
    }

    public PowerSupply(
            String brand,
            String model,
            BigDecimal referencePrice,
            int wattage,
            PsuFormFactor formFactor,
            PsuEfficiencyRating efficiencyRating,
            PsuModularity modularity
    ) {

        super(
                ComponentCategory.POWER_SUPPLY,
                brand,
                model,
                referencePrice
        );

        this.wattage = wattage;
        this.formFactor = formFactor;
        this.efficiencyRating = efficiencyRating;
        this.modularity = modularity;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    public PsuFormFactor getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(PsuFormFactor formFactor) {
        this.formFactor = formFactor;
    }

    public PsuEfficiencyRating getEfficiencyRating() {
        return efficiencyRating;
    }

    public void setEfficiencyRating(PsuEfficiencyRating efficiencyRating) {
        this.efficiencyRating = efficiencyRating;
    }

    public PsuModularity getModularity() {
        return modularity;
    }

    public void setModularity(PsuModularity modularity) {
        this.modularity = modularity;
    }
}