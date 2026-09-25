package com.ryvex.server.model;

import com.ryvex.server.model.hardware.*;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "pc_builds",
        indexes = {
                @Index(
                        name = "idx_pc_builds_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_pc_builds_created_at",
                        columnList = "created_at"
                )
        }
)
public class PcBuild {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Version
    private Long version;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_id")
    private Cpu cpu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_id")
    private Motherboard motherboard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_cooler_id")
    private CpuCooler cpuCooler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id")
    private Memory memory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gpu_id")
    private Gpu gpu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_supply_id")
    private PowerSupply powerSupply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private PcCase pcCase;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    protected PcBuild() {
    }

    public PcBuild(
            User user,
            String name
    ) {

        this.user =
                user;

        this.name =
                name;
    }

    @PrePersist
    private void onCreate() {

        Instant now =
                Instant.now();

        createdAt =
                now;

        updatedAt =
                now;
    }

    @PreUpdate
    private void onUpdate() {

        updatedAt =
                Instant.now();
    }

    public Long getId() {

        return id;
    }

    public Long getVersion() {

        return version;
    }

    public User getUser() {

        return user;
    }

    public void setUser(
            User user
    ) {

        this.user =
                user;
    }

    public String getName() {

        return name;
    }

    public void setName(
            String name
    ) {

        this.name =
                name;
    }

    public Cpu getCpu() {

        return cpu;
    }

    public void setCpu(
            Cpu cpu
    ) {

        this.cpu =
                cpu;
    }

    public Motherboard getMotherboard() {

        return motherboard;
    }

    public void setMotherboard(
            Motherboard motherboard
    ) {

        this.motherboard =
                motherboard;
    }

    public CpuCooler getCpuCooler() {

        return cpuCooler;
    }

    public void setCpuCooler(
            CpuCooler cpuCooler
    ) {

        this.cpuCooler =
                cpuCooler;
    }

    public Memory getMemory() {

        return memory;
    }

    public void setMemory(
            Memory memory
    ) {

        this.memory =
                memory;
    }

    public Gpu getGpu() {

        return gpu;
    }

    public void setGpu(
            Gpu gpu
    ) {

        this.gpu =
                gpu;
    }

    public Storage getStorage() {

        return storage;
    }

    public void setStorage(
            Storage storage
    ) {

        this.storage =
                storage;
    }

    public PowerSupply getPowerSupply() {

        return powerSupply;
    }

    public void setPowerSupply(
            PowerSupply powerSupply
    ) {

        this.powerSupply =
                powerSupply;
    }

    public PcCase getPcCase() {

        return pcCase;
    }

    public void setPcCase(
            PcCase pcCase
    ) {

        this.pcCase =
                pcCase;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

    public Instant getUpdatedAt() {

        return updatedAt;
    }
}