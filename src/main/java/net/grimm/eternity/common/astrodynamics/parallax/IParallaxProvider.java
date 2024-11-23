package net.grimm.eternity.common.astrodynamics.parallax;

import org.joml.Vector3d;

public interface IParallaxProvider {

    double getRadius();

    Vector3d getPosition(long epoch);

}
