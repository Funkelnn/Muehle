import processing.core.PApplet;

/**
 * Just a basic Processing template to check if your IDE is configured correctly.
 */
public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Main.class);
    }

    public Main() {}

    @Override
    public void settings() {
        setSize(600, 400);
        pixelDensity(2);
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
        setSize(1000,1000); //Bildschirmgröße
        background(255); //Hintergrundfarbe
                
    }
}
