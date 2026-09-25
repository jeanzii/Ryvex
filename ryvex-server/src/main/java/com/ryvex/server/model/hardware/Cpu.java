package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.CpuSocket;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "cpus"
)
public class Cpu extends HardwareComponent {

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "socket",
            nullable = false,
            length = 30
    )
    private CpuSocket socket;

    @Column(
            name = "core_count",
            nullable = false
    )
    private int coreCount;

    @Column(
            name = "thread_count",
            nullable = false
    )
    private int threadCount;

    @Column(
            name = "base_clock_ghz",
            precision = 4,
            scale = 2
    )
    private BigDecimal baseClockGhz;

    @Column(
            name = "boost_clock_ghz",
            precision = 4,
            scale = 2
    )
    private BigDecimal boostClockGhz;

    @Column(
            name = "tdp_watts"
    )
    private Integer tdpWatts;

    @Column(
            name = "integrated_graphics",
            nullable = false
    )
    private boolean integratedGraphics;

    protected Cpu() {
    }

    public Cpu(
            String brand,
            String model,
            BigDecimal referencePrice,
            CpuSocket socket,
            int coreCount,
            int threadCount
    ) {

        super(
                ComponentCategory.CPU,
                brand,
                model,
                referencePrice
        );

        this.socket =
                socket;

        this.coreCount =
                coreCount;

        this.threadCount =
                threadCount;
    }

    public CpuSocket getSocket() {

        return socket;
    }

    public void setSocket(
            CpuSocket socket
    ) {

        this.socket =
                socket;
    }

    public int getCoreCount() {

        return coreCount;
    }

    public void setCoreCount(
            int coreCount
    ) {

        this.coreCount =
                coreCount;
    }

    public int getThreadCount() {

        return threadCount;
    }

    public void setThreadCount(
            int threadCount
    ) {

        this.threadCount =
                threadCount;
    }

    public BigDecimal getBaseClockGhz() {

        return baseClockGhz;
    }

    public void setBaseClockGhz(
            BigDecimal baseClockGhz
    ) {

        this.baseClockGhz =
                baseClockGhz;
    }

    public BigDecimal getBoostClockGhz() {

        return boostClockGhz;
    }

    public void setBoostClockGhz(
            BigDecimal boostClockGhz
    ) {

        this.boostClockGhz =
                boostClockGhz;
    }

    public Integer getTdpWatts() {

        return tdpWatts;
    }

    public void setTdpWatts(
            Integer tdpWatts
    ) {

        this.tdpWatts =
                tdpWatts;
    }

    public boolean isIntegratedGraphics() {

        return integratedGraphics;
    }

    public void setIntegratedGraphics(
            boolean integratedGraphics
    ) {

        this.integratedGraphics =
                integratedGraphics;
    }
}