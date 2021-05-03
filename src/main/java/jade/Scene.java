package jade;

public abstract class Scene {
    public Scene() {
    }

    protected Camera camera;

    public void init() {

    }

    public abstract void update(float dt);


}
