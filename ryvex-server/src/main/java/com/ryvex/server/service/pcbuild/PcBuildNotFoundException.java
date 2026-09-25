package com.ryvex.server.service.pcbuild;

public class PcBuildNotFoundException
        extends RuntimeException {

    public PcBuildNotFoundException(
            Long buildId
    ) {

        super(
                "PC build "
                        + buildId
                        + " could not be found."
        );
    }
}