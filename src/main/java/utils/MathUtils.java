package utils;

import org.lwjgl.util.vector.Vector3f;

public class MathUtils {
    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector3f vectorLerp(Vector3f start, Vector3f end, float percent) {
        return Vector3f.add(start, ((Vector3f) Vector3f.sub(end, start, null).scale(percent)), null);
    }

    public static float floatLerp(float start, float end, float percent) {
        return start + (end - start) * percent;
    }
}
