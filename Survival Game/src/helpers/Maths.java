package helpers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Maths {
	
	public static double distance(Point p1, Point p2) {
		int x1 = (int) p1.getX();
		int x2 = (int) p2.getX();
		int y1 = (int) p1.getY();
		int y2 = (int) p2.getY();
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	public static Point getLineLineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
	   double det1And2 = det(x1, y1, x2, y2);
	   double det3And4 = det(x3, y3, x4, y4);
	   double x1LessX2 = x1 - x2;
	   double y1LessY2 = y1 - y2;
	   double x3LessX4 = x3 - x4;
	   double y3LessY4 = y3 - y4;
	   double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
	   if (det1Less2And3Less4 == 0){
	      // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
	      return null;
	   }
	   double x = (det(det1And2, x1LessX2,
	         det3And4, x3LessX4) /
	         det1Less2And3Less4);
	   double y = (det(det1And2, y1LessY2,
	         det3And4, y3LessY4) /
	         det1Less2And3Less4);
	   return new Point((int) x, (int) y);
	}
	
	protected static double det(double a, double b, double c, double d) {
	   return a * d - b * c;
	}
	
	public static Point getLineRectIntersect(Line2D.Double line, Rectangle rect) {
		boolean intersects = true;
		double x_min = rect.getX();
		double y_min = rect.getY();
		double x_max = rect.getX() + rect.getWidth();
		double y_max = rect.getY() + rect.getHeight();
		double x_0 = line.getX1();
		double y_0 = line.getY1();
		double x_Delta = line.getX2() - x_0;
		double y_Delta = line.getY2() - y_0;
		double u_1 = Double.MIN_VALUE;
		double u_2 = Double.MAX_VALUE;
		double[] p = {-x_Delta, x_Delta, -y_Delta, y_Delta};
		double[] q = {x_0 - x_min, x_max - x_0, y_0 - y_min, y_max - y_0};
		for (int i = 0; i < 4; i++) {
			if (p[i] == 0) {
				if (q[i] == 0) intersects = false;
			}
			else {
				double t = q[i] / p[i];
				if (p[i] < 0 && u_1 < t) {
					u_1 = t;
				}
				else if (p[i] > 0 && u_2 > t) {
					u_2 = t;
				}
			}
		}
		if (u_1 > u_2 || u_1 > 1 || u_1 < 0) intersects = false;
		
		if (!intersects) return null;
		else {
			return new Point((int) (x_0 + u_1 * x_Delta), (int) (y_0 + u_1 * y_Delta));
		}
	}
}