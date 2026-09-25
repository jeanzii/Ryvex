package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import com.ryvex.server.model.hardware.enumtype.StorageInterface;
import com.ryvex.server.model.hardware.enumtype.StorageType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "storage_devices"
)
public class Storage extends HardwareComponent {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "storage_type",
            nullable = false,
            length = 20
    )
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "storage_interface",
            nullable = false,
            length = 30
    )
    private StorageInterface storageInterface;

    @Column(
            name = "capacity_gb",
            nullable = false
    )
    private int capacityGb;

    @Column(
            name = "read_speed_mbps"
    )
    private Integer readSpeedMbps;

    @Column(
            name = "write_speed_mbps"
    )
    private Integer writeSpeedMbps;

    protected Storage() {
    }

    public Storage(
            String brand,
            String model,
            BigDecimal referencePrice,
            StorageType storageType,
            StorageInterface storageInterface,
            int capacityGb
    ) {

        super(
                ComponentCategory.STORAGE,
                brand,
                model,
                referencePrice
        );

        this.storageType = storageType;
        this.storageInterface = storageInterface;
        this.capacityGb = capacityGb;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public StorageInterface getStorageInterface() {
        return storageInterface;
    }

    public void setStorageInterface(StorageInterface storageInterface) {
        this.storageInterface = storageInterface;
    }

    public int getCapacityGb() {
        return capacityGb;
    }

    public void setCapacityGb(int capacityGb) {
        this.capacityGb = capacityGb;
    }

    public Integer getReadSpeedMbps() {
        return readSpeedMbps;
    }

    public void setReadSpeedMbps(Integer readSpeedMbps) {
        this.readSpeedMbps = readSpeedMbps;
    }

    public Integer getWriteSpeedMbps() {
        return writeSpeedMbps;
    }

    public void setWriteSpeedMbps(Integer writeSpeedMbps) {
        this.writeSpeedMbps = writeSpeedMbps;
    }
}