package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.opengl.GL;
import util.Time;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

// On Website of OpenGL more INFO
public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    public float r,g,b,a;
    private boolean fadeToBlack=false;

    private static Window window = null;


    private static Scene currentScene;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Physics Engine";

        // At the starting, scene color
        r=1;
        g=1;
        b=0.5f;
        a=1;
    }

    public static  void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene=new LevelEditorScene();
                currentScene.init();
                break;

            case 1:
                currentScene=new LevelScene();
                currentScene.init();
                break;
            default:
                assert false: "Unknown scene '"+newScene + "' ";
                break;
        }

    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("LWJGL version : " + Version.getVersion() + "!");

        init();
        loop();

        //1. Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //2. Terminate GLFW and the free error callback
        // on Website of OpenGL more INFO
        glfwTerminate();
        glfwSetErrorCallback(null).free();// OS dont need to clean

    }

    public void init() {
        // An error callback
        GLFWErrorCallback.createPrint(System.err).set();
        //Initialize GLFW
        if (!glfwInit()) {         // Check if window create or not
            throw new IllegalStateException("Unable to Initialize GLFW");
        }
        //configure GLFW
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create the window
        // Memory address of window, glfwWindow.
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW");
        }

        // Listen and setUp mouse: After window create, we say mouse listener to window
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        // Listen and setUp key
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        //Enable v-sync, there is no time between frames.
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL

        // bindings available for use.
        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop() {
        float beginTime= Time.getTime();
        float endTime;
        float dt=-1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            //Setup window colorS
            //RGB value ->red  G     B   alpha
            glClearColor(r, g, b, a);

            //How to clear buffer that says
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0) {
                currentScene.update(dt);
            }

            // black screen. try to true
//            if(fadeToBlack){
//                r=Math.max(r-0.01f,0);
//                g=Math.max(g-0.01f,0);
//                b=Math.max(b-0.01f,0);
//            }
//            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
//                fadeToBlack=true;
//            }
//
//            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
//                fadeToBlack=true;
//            }

                glfwSwapBuffers(glfwWindow);

            endTime=Time.getTime();
            dt=endTime-beginTime;
            beginTime=endTime;
        }
    }
}
