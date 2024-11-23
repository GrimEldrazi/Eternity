package net.grimm.eternity.common.util;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.minecraft.util.Mth;

public final class DayCycleUtil {

    public static double sunAlt(Celestial celestial, long epoch) {
        double yaw = -celestial.spinProgress(epoch) + Math.PI;
        double pitch = Mth.HALF_PI - celestial.getElements().latitude() - Math.PI;
        //max alt = pitch
        //min alt = -pitch
        //on horizon at alt = 0
        //alt at max when yaw = 0
        //alt at min when yaw = pi
        //alt at horizon when yaw = pi/2 or 3*pi/2
        //alt = pitch*cos(yaw)
        return pitch * Math.cos(yaw); //TODO: find sun altitude in degrees
    }

}
