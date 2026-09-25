package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.CpuSocket;
import com.ryvex.server.model.hardware.enumtype.MemoryType;
import com.ryvex.server.model.hardware.enumtype.MotherboardFormFactor;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "motherboards"
)
public class Motherboard extends HardwareComponent {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "socket",
            nullable = false,
            length = 30
    )
    private CpuSocket socket;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "memory_type",
            nullable = false,
            length = 20
    )
    private MemoryType memoryType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "form_factor",
            nullable = false,
            length = 30
    )
    private MotherboardFormFactor formFactor;

    @Column(
            name = "chipset",
            length = 50
    )
    private String chipset;

    @Column(
            name = "memory_slots"
    )
    private Integer memorySlots;

    @Column(
            name = "max_memory_gb"
    )
    private Integer maxMemoryGb;

    @Column(
            name = "m2_slots"
    )
    private Integer m2Slots;

    protected Motherboard() {
    }

    public Motherboard(
            String brand,
            String model,
            BigDecimal referencePrice,
            CpuSocket socket,
            MemoryType memoryType,
            MotherboardFormFactor formFactor
    ) {

        super(
                ComponentCategory.MOTHERBOARD,
                brand,
                model,
                referencePrice
        );

        this.socket = socket;
        this.memoryType = memoryType;
        this.formFactor = formFactor;
    }

    public CpuSocket getSocket() {
        return socket;
    }

    public void setSocket(CpuSocket socket) {
        this.socket = socket;
    }

    public MemoryType getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(MemoryType memoryType) {
        this.memoryType = memoryType;
    }

    public MotherboardFormFactor getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(MotherboardFormFactor formFactor) {
        this.formFactor = formFactor;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public Integer getMemorySlots() {
        return memorySlots;
    }

    public void setMemorySlots(Integer memorySlots) {
        this.memorySlots = memorySlots;
    }

    public Integer getMaxMemoryGb() {
        return maxMemoryGb;
    }

    public void setMaxMemoryGb(Integer maxMemoryGb) {
        this.maxMemoryGb = maxMemoryGb;
    }

    public Integer getM2Slots() {
        return m2Slots;
    }

    public void setM2Slots(Integer m2Slots) {
        this.m2Slots = m2Slots;
    }
}