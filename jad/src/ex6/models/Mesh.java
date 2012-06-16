/*     */ package ex6.models;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.media.opengl.GL;
/*     */ 
/*     */ public class Mesh
/*     */ {
/*     */   public ArrayList<int[]> faces;
/*     */   public ArrayList<Vec> vertices;
/*     */   public ArrayList<Vec> normals;
/*     */ 
/*     */   public Mesh()
/*     */   {
/*  20 */     this.faces = new ArrayList();
/*  21 */     this.vertices = new ArrayList();
/*  22 */     this.normals = new ArrayList();
/*     */   }
/*     */ 
/*     */   public Mesh(ArrayList<int[]> faces, ArrayList<Vec> vertices) {
/*  26 */     this.faces = faces;
/*  27 */     this.vertices = vertices;
/*  28 */     calculateNormals();
/*     */   }
/*     */ 
/*     */   public Mesh(Mesh s)
/*     */   {
/*  33 */     this.faces = ((ArrayList)s.faces.clone());
/*  34 */     this.vertices = ((ArrayList)s.vertices.clone());
/*  35 */     calculateNormals();
/*     */   }
/*     */ 
/*     */   public void calculateNormals() {
/*  39 */     this.normals = new ArrayList(this.vertices.size());
/*  40 */     for (int i = 0; i < this.vertices.size(); i++) {
/*  41 */       this.normals.add(new Vec());
/*     */     }
/*     */ 
/*  44 */     for (int[] f : this.faces) {
/*  45 */       for (int vi : f) {
/*  46 */         Vec v0 = (Vec)this.vertices.get(f[0]);
/*  47 */         Vec v1 = (Vec)this.vertices.get(f[1]);
/*  48 */         Vec v2 = (Vec)this.vertices.get(f[2]);
/*  49 */         Vec norm = Vec.crossProd(Vec.sub(v1, v0), Vec.sub(v2, v0));
/*  50 */         ((Vec)this.normals.get(vi)).add(norm);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  55 */     for (int i = 0; i < this.vertices.size(); i++)
/*  56 */       ((Vec)this.normals.get(i)).normalize();
/*     */   }
/*     */ 
/*     */   public void subdivide(int levels)
/*     */   {
/*  61 */     for (int i = 0; i < levels; i++) {
/*  62 */       refineTopology();
/*  63 */       refineGeometry();
/*     */     }
/*  65 */     calculateNormals();
/*     */   }
/*     */ 
/*     */   private void refineTopology()
/*     */   {
/* 101 */     Mesh newMesh = new Mesh();
/* 102 */     newMesh.vertices = ((ArrayList)this.vertices.clone());
/* 103 */     EdgeMap edges = new EdgeMap(null);
/* 104 */     int edgeCtrOffset = this.vertices.size() + this.faces.size();
/*     */ 
/* 106 */     for (int[] f : this.faces)
/*     */     {
/* 108 */       Vec centroid = new Vec();
/* 109 */       for (int vi : f) {
/* 110 */         Vec v = (Vec)this.vertices.get(vi);
/* 111 */         centroid.add(v);
/*     */       }
/* 113 */       centroid.mult(1.0D / f.length);
/*     */ 
/* 116 */       int sz = newMesh.vertices.size();
/* 117 */       newMesh.vertices.add(centroid);
/*     */ 
/* 120 */       int nVert = f.length;
/* 121 */       int[] edgeCtr = new int[nVert];
/* 122 */       for (int i = 0; i < nVert; i++) {
/* 123 */         edgeCtr[i] = edges.add(f[i], f[((i + 1) % nVert)]);
/*     */       }
/*     */ 
/* 127 */       for (int i = 0; i < f.length; i++) {
/* 128 */         int[] nf = { sz, 
/* 129 */           edgeCtrOffset + edgeCtr[i], 
/* 130 */           f[((i + 1) % nVert)], 
/* 131 */           edgeCtrOffset + edgeCtr[((i + 1) % nVert)] };
/* 132 */         newMesh.faces.add(nf);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 137 */     newMesh.vertices.addAll(edges.get());
/*     */ 
/* 139 */     this.vertices = newMesh.vertices;
/* 140 */     this.faces = newMesh.faces;
/*     */   }
/*     */ 
/*     */   private void refineGeometry()
/*     */   {
/* 145 */     ArrayList newV = new ArrayList(this.vertices.size());
/* 146 */     int[] count = new int[this.vertices.size()];
/* 147 */     for (int i = 0; i < this.vertices.size(); i++) {
/* 148 */       newV.add(new Vec());
/* 149 */       count[i] = 0;
/*     */     }
/*     */ 
/* 153 */     for (int[] f : this.faces)
/*     */     {
/* 155 */       Vec centroid = new Vec();
/* 156 */       for (int vi : f) {
/* 157 */         Vec v = (Vec)this.vertices.get(vi);
/* 158 */         centroid.add(v);
/*     */       }
/* 160 */       centroid.mult(1.0D / f.length);
/*     */ 
/* 162 */       for (int vi : f) {
/* 163 */         ((Vec)newV.get(vi)).add(centroid);
/* 164 */         count[vi] += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 169 */     for (int i = 0; i < this.vertices.size(); i++) {
/* 170 */       ((Vec)newV.get(i)).mult(1.0D / count[i]);
/*     */     }
/*     */ 
/* 173 */     this.vertices = newV;
/*     */   }
/*     */ 
/*     */   public void draw(GL gl)
/*     */   {
/* 178 */     for (int[] f : this.faces) {
/* 179 */       gl.glBegin(9);
/*     */ 
/* 181 */       for (int vi : f) {
/* 182 */         Vec norm = (Vec)this.normals.get(vi);
/* 183 */         gl.glNormal3d(norm.x, norm.y, norm.z);
/* 184 */         gl.glVertex3d(((Vec)this.vertices.get(vi)).x, ((Vec)this.vertices.get(vi)).y, ((Vec)this.vertices.get(vi)).z);
/*     */       }
/*     */ 
/* 187 */       gl.glEnd();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class EdgeMap
/*     */   {
/*  74 */     private HashMap<Integer, Integer> map = new HashMap();
/*  75 */     private Vector<Vec> centroids = new Vector();
/*     */ 
/*     */     private EdgeMap() {
/*     */     }
/*  79 */     public int add(int vi1, int vi2) { int vmin = Math.min(vi1, vi2);
/*  80 */       int vmax = Math.max(vi1, vi2);
/*  81 */       int key = vmax << 16 | vmin;
/*  82 */       Integer pos = (Integer)this.map.get(Integer.valueOf(key));
/*  83 */       if (pos == null) {
/*  84 */         pos = Integer.valueOf(this.centroids.size());
/*  85 */         this.map.put(Integer.valueOf(key), pos);
/*  86 */         this.centroids.add(Vec.mult(0.5D, Vec.add((Vec)Mesh.this.vertices.get(vi1), (Vec)Mesh.this.vertices.get(vi2))));
/*  87 */         return pos.intValue();
/*     */       }
/*  89 */       return pos.intValue();
/*     */     }
/*     */ 
/*     */     public Vector<Vec> get()
/*     */     {
/*  94 */       return this.centroids;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.models.Mesh
 * JD-Core Version:    0.6.0
 */