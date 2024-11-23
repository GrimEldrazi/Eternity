package net.grimm.eternity.common.astrodynamics.parallax;

import org.joml.Vector3d;

public class Parallax {

    private final Vector3d pos0;
    private final Vector3d pos1;

    public Parallax(Vector3d pos0, Vector3d pos1) {
        this.pos0 = pos0;
        this.pos1 = pos1;
    }
}
