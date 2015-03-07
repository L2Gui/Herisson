package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

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
}
