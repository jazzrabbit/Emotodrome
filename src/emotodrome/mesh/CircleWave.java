package emotodrome.mesh;

import javax.microedition.khronos.opengles.GL10;

public class CircleWave extends Mesh{
	
	private Group circles;
	private float dR;
	private int numCircles;
	private float speed;
	private float[] outerRadii;
	private float[] innerRadii;
	private float maxRadius;
	private float minRadius;
	private float spacing;
	private float[][] colors;
	private float[] heights;
	
	/**
	 * @param numCircles - number of circles in this wave
	 * @param speed - the speed at which the circles move outward
	 * @param minOuterRadius - the smallest radius possible for the outer edge of each circle
	 * @param maxOuterRadius - the largest radius possible for the outer edge of each circle
	 * @param minInnerRadius - the smallest radius possible for the inner edge of each circle
	 * @param maxInnerRadius - the largest radius possible for the inner edge of each circle
	 * @param spacing - space between each circle
	 * @param minY - minimum height of a circle
	 * @param maxY - maximum height of a circle
	 * @param colorMin - one end of the color spectrum these circles will be drawn with
	 * @param colorMax - other end of color spectrum
	 */
	public CircleWave(int numCircles, float speed, float minOuterRadius, float maxOuterRadius, float minInnerRadius, float maxInnerRadius, float spacing, float minY, float maxY, float[] colorMin, float[] colorMax){
		this.dR = ((maxOuterRadius - minOuterRadius)/numCircles);
		this.numCircles = numCircles;
		this.speed = speed;
		this.outerRadii = new float[numCircles];
		this.innerRadii = new float[numCircles];
		this.maxRadius = maxOuterRadius;
		this.minRadius = minOuterRadius;
		this.colors = new float[numCircles][4];
		this.heights = new float[numCircles];
		
		circles = new Group();
		for (int i = 0; i < numCircles; i++){
			outerRadii[i] = minOuterRadius + dR * i;
			innerRadii[i] = minInnerRadius + dR * i;
			colors[i][0] = (float) (Math.random() * (colorMax[0] - colorMin[0]) + colorMin[0]);
			colors[i][1] = (float) (Math.random() * (colorMax[1] - colorMin[1]) + colorMin[1]);
			colors[i][2] = (float) (Math.random() * (colorMax[2] - colorMin[2]) + colorMin[2]);
			colors[i][3] = (float) (Math.random() * (colorMax[3] - colorMin[3]) + colorMin[3]);
			Circle c = new Circle(outerRadii[i], innerRadii[i], colors[i]);
			heights[i] = (float) Math.random() * (maxY - minY) + minY;
			c.y = heights[i];
			circles.add(i, c);
		}
	}
	
	public void draw(GL10 gl){
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		for (int i = 0; i < numCircles; i++){
			outerRadii[i] += speed;
			if (outerRadii[i] > maxRadius * (i + 1))
				outerRadii[i] = minRadius * (i + 1);
			Circle c = (Circle) circles.get(i);
			c.setRadius(outerRadii[i]);
			gl.glPushMatrix();
			c.draw(gl);
			gl.glPopMatrix();
			
		}
		gl.glEnable(GL10.GL_TEXTURE_2D);
	}

}
