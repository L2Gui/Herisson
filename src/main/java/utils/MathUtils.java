package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class MathUtils {
	public static Matrix4f quaternionToMatrix(Quaternion quat) {
		Matrix4f matrix = new Matrix4f();
		
		float xx = quat.x * quat.x;
		float xy = quat.x * quat.y;
		float xz = quat.x * quat.z;
		float xw = quat.x * quat.w;

		float yy = quat.y * quat.y;
		float yz = quat.y * quat.z;
		float yw = quat.y * quat.w;

		float zz = quat.z * quat.z;
		float zw = quat.z * quat.w;

		matrix.m00 = 1 - 2 * (yy + zz);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);

		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xx + zz);
		matrix.m12 = 2 * (yz - xw);

		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xx + yy);
		
		return matrix;
	}

    public static Quaternion invertQuaternion(Quaternion quat) {
        Quaternion newQuat = new Quaternion(quat);
        newQuat.setX(- quat.getX());
        newQuat.setY(- quat.getY());
        newQuat.setZ(- quat.getZ());
        return newQuat;
    }

    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector3f Lerp(Vector3f start, Vector3f end, float percent)
    {
        return Vector3f.add(start, ((Vector3f) Vector3f.sub(end, start, null).scale(percent)), null);
    }

    Vector3f Slerp(Vector3f start, Vector3f end, float percent)
    {
        // To avoid side effect
        Vector3f pstart = new Vector3f(start);

        // Dot product - the cosine of the angle between 2 vectors.
        float dot = Vector3f.dot(start, end);
        // Clamp it to be in the range of Acos()
        // This may be unnecessary, but floating point
        // precision can be a fickle mistress.
        MathUtils.clamp(dot, -1.0f, 1.0f);
        // Acos(dot) returns the angle between start and end,
        // And multiplying that by percent returns the angle between
        // start and the final result.
        float theta = (float) Math.acos(dot) * percent;

        Vector3f relativeVec = Vector3f.sub(end, (Vector3f) pstart.scale(dot), null);
        pstart = new Vector3f(start);

        relativeVec.normalise();     // Orthonormal basis
        // The final result.
        return Vector3f.add((Vector3f) pstart.scale((float) Math.cos(theta)), (Vector3f) relativeVec.scale((float) Math.sin(theta)), null);
    }
}
