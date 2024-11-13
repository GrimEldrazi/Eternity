package net.grimm.eternity.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EMath {

    public static double newtonMethod(double initial, int iterations, Function<Double, Double> baseFunction, Function<Double, Double> firstDerivative) {
        double approxValue = initial;
        for (int i = 1; i < iterations; i++) {
            approxValue = approxValue - baseFunction.apply(approxValue) / firstDerivative.apply(approxValue);
        }
        return approxValue;
    }

    public static double round(double value, int place) {
        if (place < 0) throw new IllegalArgumentException();
        if (Double.isNaN(value)) return Double.NaN;
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(place, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
