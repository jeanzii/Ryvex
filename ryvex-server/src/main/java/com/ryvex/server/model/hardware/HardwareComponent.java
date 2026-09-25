package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "hardware_components",
        indexes = {
                @Index(
                        name = "idx_hardware_component_category",
                        columnList = "category"
                ),
                @Index(
                        name = "idx_hardware_component_brand",
                        columnList = "brand"
                ),
                @Index(
                        name = "idx_hardware_component_model",
                        columnList = "model"
                )
        }
)
@Inheritance(
        strategy = InheritanceType.JOINED
)
public abstract class HardwareComponent {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "category",
            nullable = false,
            length = 30
    )
    private ComponentCategory category;

    @Column(
            name = "brand",
            nullable = false,
            length = 80
    )
    private String brand;

    @Column(
            name = "model",
            nullable = false,
            length = 150
    )
    private String model;

    @Column(
            name = "reference_price",
            precision = 12,
            scale = 2
    )
    private BigDecimal referencePrice;

    @Column(
            name = "currency_code",
            nullable = false,
            length = 3
    )
    private String currencyCode = "EUR";

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

    protected HardwareComponent() {
    }

    protected HardwareComponent(
            ComponentCategory category,
            String brand,
            String model,
            BigDecimal referencePrice
    ) {

        this.category =
                category;

        this.brand =
                brand;

        this.model =
                model;

        this.referencePrice =
                referencePrice;
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

    public ComponentCategory getCategory() {

        return category;
    }

    public String getBrand() {

        return brand;
    }

    public void setBrand(
            String brand
    ) {

        this.brand =
                brand;
    }

    public String getModel() {

        return model;
    }

    public void setModel(
            String model
    ) {

        this.model =
                model;
    }

    public BigDecimal getReferencePrice() {

        return referencePrice;
    }

    public void setReferencePrice(
            BigDecimal referencePrice
    ) {

        this.referencePrice =
                referencePrice;
    }

    public String getCurrencyCode() {

        return currencyCode;
    }

    public void setCurrencyCode(
            String currencyCode
    ) {

        this.currencyCode =
                currencyCode;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

    public Instant getUpdatedAt() {

        return updatedAt;
    }
}