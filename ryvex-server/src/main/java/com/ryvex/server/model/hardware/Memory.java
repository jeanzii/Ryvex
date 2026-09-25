package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.MemoryType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "memory_kits"
)
public class Memory extends HardwareComponent {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "memory_type",
            nullable = false,
            length = 20
    )
    private MemoryType memoryType;

    @Column(
            name = "capacity_gb",
            nullable = false
    )
    private int capacityGb;

    @Column(
            name = "module_count",
            nullable = false
    )
    private int moduleCount;

    @Column(
            name = "speed_mts"
    )
    private Integer speedMts;

    @Column(
            name = "cas_latency"
    )
    private Integer casLatency;

    protected Memory() {
    }

    public Memory(
            String brand,
            String model,
            BigDecimal referencePrice,
            MemoryType memoryType,
            int capacityGb,
            int moduleCount
    ) {

        super(
                ComponentCategory.MEMORY,
                brand,
                model,
                referencePrice
        );

        this.memoryType = memoryType;
        this.capacityGb = capacityGb;
        this.moduleCount = moduleCount;
    }

    public MemoryType getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(MemoryType memoryType) {
        this.memoryType = memoryType;
    }

    public int getCapacityGb() {
        return capacityGb;
    }

    public void setCapacityGb(int capacityGb) {
        this.capacityGb = capacityGb;
    }

    public int getModuleCount() {
        return moduleCount;
    }

    public void setModuleCount(int moduleCount) {
        this.moduleCount = moduleCount;
    }

    public Integer getSpeedMts() {
        return speedMts;
    }

    public void setSpeedMts(Integer speedMts) {
        this.speedMts = speedMts;
    }

    public Integer getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(Integer casLatency) {
        this.casLatency = casLatency;
    }
}