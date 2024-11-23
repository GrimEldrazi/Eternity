package net.grimm.eternity.common.astrodynamics.parallax;

import net.grimm.eternity.common.util.EMath;
import net.grimm.eternity.common.util.SphericalVector3d;
import org.joml.Vector3d;

public class ParallaxTEST {

    protected final Vector3d position;
    protected final IParallaxProvider provider;
    protected final long epoch;

    public ParallaxTEST(Vector3d position, IParallaxProvider provider, long epoch) {
        this.position = position;
        this.provider = provider;
        this.epoch = epoch;
    }

    public double distanceTo() {
        return position.distance(provider.getPosition(epoch));
    }

    public double apparentSize() {
        return Math.atan(provider.getRadius() / distanceTo()) * 2;
    }

    public double apparentRadius() {
        return apparentSize() / 2;
    }

    public SphericalVector3d sphericalCoordinates() {
        return EMath.toSpherical(provider.getPosition(epoch).sub(position));
    }

}
