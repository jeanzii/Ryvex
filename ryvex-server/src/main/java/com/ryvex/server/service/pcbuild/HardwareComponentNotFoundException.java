package com.ryvex.server.service.pcbuild;

public class HardwareComponentNotFoundException
        extends RuntimeException {

    public HardwareComponentNotFoundException(
            Long componentId
    ) {

        super(
                "Hardware component "
                        + componentId
                        + " could not be found."
        );
    }
}