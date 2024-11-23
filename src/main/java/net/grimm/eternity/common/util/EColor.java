package net.grimm.eternity.common.util;

import java.awt.*;
import java.util.List;

public class EColor {

    private float red;
    private float green;
    private float blue;
    private float alpha;

    public EColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public EColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 255;
    }

    public EColor(List<Integer> colorList) {
        this.red = colorList.get(0);
        this.green = colorList.get(1);
        this.blue = colorList.get(2);
        this.alpha = 255;
    }

    public EColor(int colorInt) {
        Color color = new Color(colorInt);
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = 255;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isNorm() {
        return red <= 1 && green <= 1 && blue <= 1 && alpha <= 1;
    }

    public EColor norm() {
        return isNorm() ? this : new EColor(this.red / 255, this.green / 255, this.blue / 255, this.alpha / 255);
    }

    public EColor deNorm() {
        return isNorm() ? new EColor(this.red * 255, this.green * 255, this.blue * 255, this.alpha * 255) : this;
    }

    public int toInt() {
        EColor color = norm();
        return new Color(color.red, color.green, color.blue).getRGB();
    }

}
