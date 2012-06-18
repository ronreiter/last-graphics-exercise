package ex6;


import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

import javax.media.opengl.GLException;
import java.io.File;
import java.io.IOException;

/**
 * Here we store all textures.
 */

public class Textures {
	public static Texture engineTexture;
	public static Texture asteroidTexture;
	public static Texture starsTexture;

	public static void load() {
		File texturePath = new File("res", File.separator);

		try
		{
			File starsTexturesFile = new File(texturePath, "stars.png");
			starsTexture = TextureIO.newTexture(starsTexturesFile, true);

			File meteorTextureFile = new File(texturePath, "asteroid.jpg");
			asteroidTexture = TextureIO.newTexture(meteorTextureFile, true);

			File engineTextureFile = new File(texturePath, "fire.jpg");
			engineTexture = TextureIO.newTexture(engineTextureFile, true);

		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
