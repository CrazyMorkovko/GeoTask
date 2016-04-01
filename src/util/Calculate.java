package util;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Calculate {

    /**
     * Convert degrees to radians
     *
     * @param deg - degrees
     * @param min - minutes
     * @param sec - seconds
     * @return angle
     */
    public static Double toRad(int deg, int min, int sec)
    {
        return (deg + (min + sec / 60.0f) / 60.0f) * Math.PI / 180;
    }

    /**
     * Calc ctg
     *
     * @param rad - radians
     * @return ctg
     */
    public static Double ctg(Double rad)
    {
        return 1 / Math.tan(rad);
    }

    /**
     * Calc direction angle
     *
     * @param r
     * @param dx
     * @param dy
     * @return angle
     */
    public static Double getGzn(Double r, Double dx, Double dy){
        Double angle = 0.0;

        if (dx > 0 && dy > 0)
            angle = r;
        if (dx > 0 && dy < 0)
            angle = 2 * Math.PI - r;
        if (dx < 0 && dy > 0)
            angle = Math.PI - r;
        if (dx < 0 && dy < 0)
            angle = Math.PI + r;

        return angle;
    }
}
