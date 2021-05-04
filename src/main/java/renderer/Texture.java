package renderer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private String filePath;
    private int texID;

    public Texture(String filePath) {

        this.filePath=filePath;

        // Generate texture on GPU
        texID=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,texID);

        //Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
    }
}
