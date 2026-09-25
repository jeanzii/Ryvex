package com.ryvex.server.model.hardware;

import com.ryvex.server.model.hardware.enumtype.ComponentCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(
        name = "gpus"
)
public class Gpu extends HardwareComponent {

    @Column(
            name = "vram_gb"
    )
    private Integer vramGb;

    @Column(
            name = "length_mm"
    )
    private Integer lengthMm;

    @Column(
            name = "tdp_watts"
    )
    private Integer tdpWatts;

    @Column(
            name = "recommended_psu_watts"
    )
    private Integer recommendedPsuWatts;

    protected Gpu() {
    }

    public Gpu(
            String brand,
            String model,
            BigDecimal referencePrice
    ) {

        super(
                ComponentCategory.GPU,
                brand,
                model,
                referencePrice
        );
    }

    public Integer getVramGb() {

        return vramGb;
    }

    public void setVramGb(
            Integer vramGb
    ) {

        this.vramGb =
                vramGb;
    }

    public Integer getLengthMm() {

        return lengthMm;
    }

    public void setLengthMm(
            Integer lengthMm
    ) {

        this.lengthMm =
                lengthMm;
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

    public Integer getRecommendedPsuWatts() {

        return recommendedPsuWatts;
    }

    public void setRecommendedPsuWatts(
            Integer recommendedPsuWatts
    ) {

        this.recommendedPsuWatts =
                recommendedPsuWatts;
    }
}