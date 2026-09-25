package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.MotherboardFormFactor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "pc_cases"
)
public class PcCase extends HardwareComponent {

    @ElementCollection(
            fetch = FetchType.LAZY
    )
    @CollectionTable(
            name = "pc_case_supported_motherboard_form_factors",
            joinColumns = @JoinColumn(
                    name = "case_id"
            )
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "form_factor",
            nullable = false,
            length = 30
    )
    private Set<MotherboardFormFactor> supportedMotherboardFormFactors =
            new HashSet<>();

    @Column(
            name = "max_gpu_length_mm"
    )
    private Integer maxGpuLengthMm;

    @Column(
            name = "max_cpu_cooler_height_mm"
    )
    private Integer maxCpuCoolerHeightMm;

    @Column(
            name = "max_radiator_size_mm"
    )
    private Integer maxRadiatorSizeMm;

    protected PcCase() {
    }

    public PcCase(
            String brand,
            String model,
            BigDecimal referencePrice
    ) {

        super(
                ComponentCategory.CASE,
                brand,
                model,
                referencePrice
        );
    }

    public Set<MotherboardFormFactor> getSupportedMotherboardFormFactors() {
        return supportedMotherboardFormFactors;
    }

    public void setSupportedMotherboardFormFactors(
            Set<MotherboardFormFactor> supportedMotherboardFormFactors
    ) {

        this.supportedMotherboardFormFactors =
                supportedMotherboardFormFactors;
    }

    public Integer getMaxGpuLengthMm() {
        return maxGpuLengthMm;
    }

    public void setMaxGpuLengthMm(Integer maxGpuLengthMm) {
        this.maxGpuLengthMm = maxGpuLengthMm;
    }

    public Integer getMaxCpuCoolerHeightMm() {
        return maxCpuCoolerHeightMm;
    }

    public void setMaxCpuCoolerHeightMm(Integer maxCpuCoolerHeightMm) {
        this.maxCpuCoolerHeightMm = maxCpuCoolerHeightMm;
    }

    public Integer getMaxRadiatorSizeMm() {
        return maxRadiatorSizeMm;
    }

    public void setMaxRadiatorSizeMm(Integer maxRadiatorSizeMm) {
        this.maxRadiatorSizeMm = maxRadiatorSizeMm;
    }
}