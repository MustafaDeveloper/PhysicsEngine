package jade;


import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import renderer.Shader;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene {

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position          //color
          100.5f,   0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1, 0,    //Bottom right    0
            0.0f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 1,    //Top left        1
          100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 1,    //Top right       2
            0.5f,   0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 0,    //Bottom left     3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {

            /*
            x       x

            x       x

             */
            2, 1, 0, //Top right triangle
            0, 1, 3, //bottom left triangle

    };

    private int vaoID, vboID, eboID; //vertexArrayObject, vertexBufferObject, elementBufferObject

    private Shader defaultShader;

    public LevelEditorScene() {

        System.out.println("Here is LevelEditorScene!");
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        //Generate VAO,VBO and EBO buffer objects and send to GPU Graphic
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();


        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Add the vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int uvSize=2;
        //int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize+uvSize) * Float.BYTES; //floatSizeBytes;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0); //location 0 position

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES); //floatSizeBytes);
        glEnableVertexAttribArray(1); //color 1 position

        glVertexAttribPointer(2,uvSize,GL_FLOAT,false,vertexSizeBytes,(positionSize+colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }


    @Override
    public void update(float dt) {
        camera.position.x -= dt * 50.0f;
        camera.position.y -= dt * 50.0f;

        // For documentation ->  1. https://docs.gl/sl4/all
        // For documentation ->  2. https://www.khronos.org/opengl/wiki
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        //Bind the VAO that we`re using
        glBindVertexArray(vaoID);

        //Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind everthing
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0); //use nothing

        defaultShader.detach(); // dont use  Program


    }
}
