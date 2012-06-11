package ex6.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.media.opengl.GL;

/* This class defines a mesh of polygons, and provides the following functionality:
 * 1. Perform surface subdivision, including average normal computation for each vertex.
 * 2. Display the mesh along with the normals at each vertex.
 */
public class Mesh {
	public ArrayList<int[]> faces;
	public ArrayList<Vec> vertices;
	public ArrayList<Vec> normals;

	public Mesh(ArrayList<int[]> faces, ArrayList<Vec> vertices) {
		if (faces == null) {
			throw new IllegalArgumentException("faces is null!");
		}
		if (vertices == null) {
			throw new IllegalArgumentException("vertices is null!");
		}

		this.faces = faces;
		this.vertices = vertices;

		calculateNormals();
	}

	public Mesh(Mesh mesh) {
		if (mesh == null) {
			throw new IllegalArgumentException("No mesh!");
		}

		this.faces = mesh.faces;
		this.vertices = mesh.vertices;
		calculateNormals();
	}

	public void subdivide(int levels) {
		for (int i = 0; i < levels; i++) {
			refineTopology();
			refineGeometry();
		}
		calculateNormals();
	}
	
	public void calculateNormals() {
		// create a new normals array
		this.normals = new ArrayList<Vec>(vertices.size());

		for (int i = 0; i < this.vertices.size(); i++) {
			this.normals.add(new Vec());
		}
		
		// Iterate thorough faces
		for (int[] face : this.faces) {
			// get the vertices according to the face index
			Vec v1 = this.vertices.get(face[0]);
			Vec v2 = this.vertices.get(face[1]);
			Vec v3 = this.vertices.get(face[2]);
			
			// calculate the normal
			Vec normal = Vec.crossProd(Vec.sub(v2, v1), Vec.sub(v3, v1));
			normal.normalize();

			// Add the generated normal to the normals array
			for (int vertexIndex : face) {
				normals.get(vertexIndex).add(normal);
			}
		}

	}
		
	private void refineTopology() {
		ArrayList<int[]> newFaces = new ArrayList<int[]>();

		for (int[] face : this.faces) {
			int newVertices[] = new int[face.length];

			Vec center = new Vec();
			for (int vertexIndex : face) {
				center.add(this.vertices.get(vertexIndex));
			}

			center.scale(1.0 / face.length);

			this.vertices.add(center);
			int centerIndex = this.vertices.size() - 1;

			// iterate over the faces to generate new, refined vertices
			for (int i = 0 ; i < face.length; i++) {
				// create a new vertex by averaging
				Vec vector = new Vec(this.vertices.get(face[i]));
				Vec nextVector = this.vertices.get(face[(i + 1) % face.length]).clone();

				vector.add(nextVector);
				vector.scale(0.5);

				if (this.vertices.indexOf(vector) == -1) {
					this.vertices.add(vector);
					newVertices[i] = this.vertices.size() - 1;
				} else {
					newVertices[i] = this.vertices.indexOf(vector);
				}

			}

			// generate the new faces
			for (int i = 0 ; i < face.length; i++) {
				int[] newFace = {
						face[i],
						newVertices[i],
						centerIndex,
						newVertices[(i + newVertices.length - 1) % newVertices.length]};

				newFaces.add(newFace);
			}
		}

		this.faces = newFaces;
	}

	private void refineGeometry() {
		ArrayList<Vec> newVertex = new ArrayList<Vec>(this.vertices.size());
		int[] vertexCount = new int[this.vertices.size()];

		for (int i = 0; i < this.vertices.size(); i++) {
			newVertex.add(new Vec());
			vertexCount[i] = 0;
		}
		
		for(int[] face : this.faces) {
			Vec center = new Vec();

			for (int vertexIndex : face) {
				center.add(vertices.get(vertexIndex));
			}

			center.scale(1.0 / face.length);
			
			for(int vertexIndex : face) {
				newVertex.get(vertexIndex).add(center);
				vertexCount[vertexIndex]++;
			}
		}

		for (int i = 0; i < this.vertices.size(); i++) {
			newVertex.get(i).scale(1.0 / vertexCount[i]);
		}
		
		this.vertices = newVertex;
	}


	public void draw(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		for (int[] face : this.faces) {
			for (int vertexIndex : face) {
				Vec normal = this.normals.get(vertexIndex);
				Vec v = this.vertices.get(vertexIndex);
				gl.glNormal3d(normal.x, normal.y, normal.z);
				gl.glVertex3d(v.x, v.y, v.z);
			}
		}
		gl.glEnd();
	}
}
