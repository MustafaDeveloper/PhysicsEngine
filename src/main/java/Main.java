import jade.Window;

import java.awt.*;


// GLFW, farklı işletim sistemlerinde basit bir şekilde OpenGL pencere oluşturmamızı ve girdileri (input) kontrol etmemize olanak sağlar.
// 1. Window klas create ettik, OpenGL ile window acilmasi ve renk ayari.
// 2. Key Listeners ve Mouse Listeners settings
// 3. Creating a Scene Manager and delta time variable
// 4. GPU Programming Works - OpenGL Graphics Programming
// 5. How OpenGl Graphics Programming Works:
//    GRAPHICS PIPELINE: (Programimiz (CPU`da) Grafik kartina (GPU) verileri bir siraya koyarak gönderir, buna PIPELINE denir)
//    VertexData[] -> Vertex Shader -> Shape Assembly -> Geometry Shader -> Rasterization (convert to Pixel)-> Fragment Shader -> Tests&Blending

// 6. Shader class; daha önce LevelEditorScene classìnda yapilanlar, Shader sinifi method ve feature ile derli-toplu hale getirildi.
// 7. Camera view; perspective 3D (far and near) and orthographic 2D
/*                                      VECTORS             CAMERA
                                    _              _     _            _
                                    | Rx  Ry  Rz  0|     | 1  0  0  -Px|
  ProjectionMatrix * LookAtMatrix = | Vx  Vy  Vz  0|  *  | 0  1  0  -Px| * aPos (world coord.)  =NDC (Normalized Device coord.: it is for different dsiplay)
                                    | Dx  Dy  Dz  0|     | 0  0  1  -Pz|
                                    | 0   0   0   1|     | 0  0  0   1 |
                                    -              -     -            -

                                    NDC=Proj * View * aPos


 */

// 8. GLSL Shaders: change color and x and y - > Rendering Color  http://science-and-fiction.org/rendering/noise.html
// 9. Texture Loading in LWJGL3

public class Main {
    public static void main(String[] args) {

        Window window=Window.get();
        window.run();


    }
}
