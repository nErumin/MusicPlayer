package utility;

public final class MathUtility {
    public static float clamp(float value, float minimum, float maximum) {
        if (value < minimum) {
            return minimum;
        } else if (value > maximum) {
            return maximum;
        }

        return value;
    }

    private MathUtility() {

    }
}
