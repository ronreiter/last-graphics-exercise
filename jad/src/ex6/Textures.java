/*    */ package ex6;
/*    */ 
/*    */ import com.sun.opengl.util.texture.Texture;
/*    */ import com.sun.opengl.util.texture.TextureIO;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.media.opengl.GLException;
/*    */ 
/*    */ public class Textures
/*    */ {
/*    */   public static Texture texSpaceshipEngine;
/*    */   public static Texture texMeteor;
/*    */   public static Texture texStars;
/*    */   public static Texture texChecker;
/*    */ 
/*    */   public static void load()
/*    */   {
/* 21 */     String texturePath = "res" + File.separator;
/* 22 */     File texStarsFile = new File(texturePath + "stars.png");
/* 23 */     File texMeteorFile = new File(texturePath + "asteroid.jpg");
/* 24 */     File texFireFile = new File(texturePath + "fire.jpg");
/* 25 */     File texCheckerFile = new File(texturePath + "checker.gif");
/*    */     try
/*    */     {
/* 28 */       texStars = TextureIO.newTexture(texStarsFile, true);
/* 29 */       texMeteor = TextureIO.newTexture(texMeteorFile, true);
/* 30 */       texSpaceshipEngine = TextureIO.newTexture(texFireFile, true);
/* 31 */       texChecker = TextureIO.newTexture(texCheckerFile, true);
/*    */     } catch (GLException e) {
/* 33 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 35 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Volumes/DataHD/Projects/Java/Graphics/last-graphics-exercise/jad/
 * Qualified Name:     ex6.Textures
 * JD-Core Version:    0.6.0
 */