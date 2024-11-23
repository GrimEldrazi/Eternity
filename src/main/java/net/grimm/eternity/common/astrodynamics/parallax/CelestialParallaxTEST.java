package net.grimm.eternity.common.astrodynamics.parallax;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.util.SphericalVector3d;

public class CelestialParallaxTEST extends ParallaxTEST {

    private final Celestial celestial;

    public CelestialParallaxTEST(Celestial celestial, IParallaxProvider provider, long epoch) {
        super(celestial.getPosition(epoch), provider, epoch);
        this.celestial = celestial;
    }

    @Override
    public SphericalVector3d sphericalCoordinates() {
        SphericalVector3d sphereCoord = new SphericalVector3d(provider.getPosition(epoch).sub(position));
        return new SphericalVector3d(sphereCoord.radius,
                -sphereCoord.polarAng - celestial.getElements().latitude(),
                sphereCoord.azimuthAng + celestial.spinProgress(epoch) + Math.PI);
    }

}
