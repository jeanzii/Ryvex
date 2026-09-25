package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.CoolerType;
import com.ryvex.server.model.hardware.enumtype.CpuSocket;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "cpu_coolers"
)
public class CpuCooler extends HardwareComponent {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "cooler_type",
            nullable = false,
            length = 20
    )
    private CoolerType coolerType;

    @ElementCollection(
            fetch = FetchType.LAZY
    )
    @CollectionTable(
            name = "cpu_cooler_supported_sockets",
            joinColumns = @JoinColumn(
                    name = "cooler_id"
            )
    )
    @Enumerated(EnumType.STRING)
    @Column(
            name = "socket",
            nullable = false,
            length = 30
    )
    private Set<CpuSocket> supportedSockets =
            new HashSet<>();

    @Column(
            name = "height_mm"
    )
    private Integer heightMm;

    @Column(
            name = "radiator_size_mm"
    )
    private Integer radiatorSizeMm;

    @Column(
            name = "rated_tdp_watts"
    )
    private Integer ratedTdpWatts;

    protected CpuCooler() {
    }

    public CpuCooler(
            String brand,
            String model,
            BigDecimal referencePrice,
            CoolerType coolerType
    ) {

        super(
                ComponentCategory.CPU_COOLER,
                brand,
                model,
                referencePrice
        );

        this.coolerType =
                coolerType;
    }

    public CoolerType getCoolerType() {
        return coolerType;
    }

    public void setCoolerType(CoolerType coolerType) {
        this.coolerType = coolerType;
    }

    public Set<CpuSocket> getSupportedSockets() {
        return supportedSockets;
    }

    public void setSupportedSockets(
            Set<CpuSocket> supportedSockets
    ) {

        this.supportedSockets =
                supportedSockets;
    }

    public Integer getHeightMm() {
        return heightMm;
    }

    public void setHeightMm(Integer heightMm) {
        this.heightMm = heightMm;
    }

    public Integer getRadiatorSizeMm() {
        return radiatorSizeMm;
    }

    public void setRadiatorSizeMm(Integer radiatorSizeMm) {
        this.radiatorSizeMm = radiatorSizeMm;
    }

    public Integer getRatedTdpWatts() {
        return ratedTdpWatts;
    }

    public void setRatedTdpWatts(Integer ratedTdpWatts) {
        this.ratedTdpWatts = ratedTdpWatts;
    }
}