package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector;
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

    public static Quaternion quaternionFromMatrix(Matrix4f matrix) {
        float num8 = matrix.m00 + matrix.m11 + matrix.m22;
        Quaternion quaternion = new Quaternion();

        if (num8 > 0f)
        {
            float num = (float) Math.sqrt(num8 + 1f);
            quaternion.w = num * 0.5f;
            num = 0.5f / num;
            quaternion.x = (matrix.m12 - matrix.m21) * num;
            quaternion.y = (matrix.m20 - matrix.m02) * num;
            quaternion.z = (matrix.m01 - matrix.m10) * num;
            return quaternion;
        }
        if ((matrix.m00 >= matrix.m11) && (matrix.m00 >= matrix.m22))
        {
            float num7 = (float)Math.sqrt(((1f + matrix.m00) - matrix.m11) - matrix.m22);
            float num4 = 0.5f / num7;
            quaternion.x = 0.5f * num7;
            quaternion.y = (matrix.m01 + matrix.m10) * num4;
            quaternion.z = (matrix.m02 + matrix.m20) * num4;
            quaternion.w = (matrix.m12 - matrix.m21) * num4;
            return quaternion;
        }
        if (matrix.m11 > matrix.m22)
        {
            float num6 = (float)Math.sqrt(((1f + matrix.m11) - matrix.m00) - matrix.m22);
            float num3 = 0.5f / num6;
            quaternion.x = (matrix.m10+ matrix.m01) * num3;
            quaternion.y = 0.5f * num6;
            quaternion.z = (matrix.m21 + matrix.m12) * num3;
            quaternion.w = (matrix.m20 - matrix.m02) * num3;
            return quaternion;
        }
        float num5 = (float) Math.sqrt(((1f + matrix.m22) - matrix.m00) - matrix.m11);
        float num2 = 0.5f / num5;
        quaternion.x = (matrix.m20 + matrix.m02) * num2;
        quaternion.y = (matrix.m21 + matrix.m12) * num2;
        quaternion.z = 0.5f * num5;
        quaternion.w = (matrix.m01 - matrix.m10) * num2;

        return quaternion;
    }

    public static Quaternion quaternionFromAxisAngle(Vector3f axis, float angle)
    {
        float halfAngle = angle * .5f;
        float s = (float) Math.sin(halfAngle);
        Quaternion q = new Quaternion();
        q.x = axis.x * s;
        q.y = axis.y * s;
        q.z = axis.z * s;
        q.w = (float) Math.cos(halfAngle);
        return q;
    }

    public static Quaternion quaternionLookRotation(Vector3f forward) {
        Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
        forward.normalise();

        Vector3f vector = new Vector3f(forward);
        Vector3f vector2 = Vector3f.cross(up, vector, null);
        vector2.normalise();
        Vector3f vector3 = Vector3f.cross(vector, vector2, null);
        vector3.normalise();

        Matrix4f mat = new Matrix4f();

        mat.m00 = vector2.x;
        mat.m01 = vector2.y;
        mat.m02 = vector2.z;
        mat.m10 = vector3.x;
        mat.m11 = vector3.y;
        mat.m12 = vector3.z;
        mat.m20 = vector.x;
        mat.m21 = vector.y;
        mat.m22 = vector.z;

        return quaternionFromMatrix(mat);
    }

    public static Quaternion invertQuaternion(Quaternion quat) {
        Quaternion newQuat = new Quaternion(quat);
        newQuat.setX(-quat.getX());
        newQuat.setY(- quat.getY());
        newQuat.setZ(-quat.getZ());
        return newQuat;
    }

    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector3f vectorLerp(Vector3f start, Vector3f end, float percent)
    {
        return Vector3f.add(start, ((Vector3f) Vector3f.sub(end, start, null).scale(percent)), null);
    }


    public static Quaternion quaternionFromEuler(Vector3f euler)
    {
        Vector3f vx = new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f vy = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f vz = new Vector3f(0.0f, 0.0f, 1.0f);

        Quaternion quatX = quaternionFromAxisAngle(vx, euler.x);
        Quaternion quatY = quaternionFromAxisAngle(vy, euler.y);
        Quaternion quatZ = quaternionFromAxisAngle(vz, euler.z);

        Quaternion quatXY = Quaternion.mul(quatX, quatY, null);
        Quaternion quat = Quaternion.mul(quatXY, quatZ, null);

        return quat;
    }

    public static Vector3f quaternionToEuler(Quaternion quat) {
        Vector3f euler = new Vector3f();
        float angle = (float) Math.acos(quat.getW()) * 2.0f;

        /* Récupération des composantes de l'axe de rotation */
        euler.setX(quat.getX());
        euler.setY(quat.getY());
        euler.setZ(quat.getZ());

        /* Normalisation de l'axe de rotation */
        float norm = (float) Math.sqrt(euler.getX() * euler.getX() + euler.getY() * euler.getY() + euler.getZ() * euler.getZ());
        if (norm > 0.0005f)
        {
            euler.x /= norm;
            euler.y /= norm;
            euler.z /= norm;
        }

        /* Calcul de la latitude */
        float latitude = - (float) Math.asin(euler.y);
        float longitude;

        /* Calcul de la longitude */
        if (euler.getX() * euler.getX() + euler.getZ() * euler.getZ() < 0.0005f)
            longitude = 0.0f;
        else
            longitude = (float) Math.atan2(euler.getX(), euler.getZ());

        /* Si la longitude est négative, on la ramène du côté positif */
        if (longitude < 0)
            longitude += 2.0f * Math.PI;

        return euler;
    }

    public static Quaternion quaternionAdd(Quaternion left, Quaternion right) {
        return new Quaternion(left.x + right.x, left.y + right.y, left.z + right.z, left.w + right.w);
    }

    public static Quaternion quaternionSlerp(Quaternion start, Quaternion end, float percent) {
        float dot = Quaternion.dot(start, end);

        Quaternion r = new Quaternion(end);
        if (dot < 0.0) {
            dot = -dot;
            r.negate();
        }

        if (1.0f - dot < 0.000001f) {
            return new Quaternion(end);
        }

        float theta = (float) Math.acos(dot);

        Quaternion slerpScaledP = new Quaternion(start);
        slerpScaledP.scale((float) Math.sin((1.0f - percent) * theta));

        Quaternion slerpScaledR = new Quaternion(r);
        slerpScaledR.scale((float) Math.sin(percent * theta));

        Quaternion result = quaternionAdd(slerpScaledP, slerpScaledR);
        result.scale((float) (1.0f / Math.sin(theta)));
        return result;
    };
}
