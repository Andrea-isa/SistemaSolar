import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class SistemaSolarMain extends SimpleApplication {
    
    private Node fixedRootNode; // Nuevo nodo raíz fijo
    private Node sun;
    private Node mercury;
    private Node venus;
    private Node earth;
    private Node moon;
    private Node mars;
    private Node jupiter;
    private Node saturn;
    private Node uranus;
    private Node neptune;

    public static void main(String[] args) {
        AppSettings setting = new AppSettings(true);
        setting.setTitle("Sistema Solar");
        SistemaSolarMain app = new SistemaSolarMain();
        app.setSettings(setting);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        fixedRootNode = new Node("FixedRootNode"); // Crear el nuevo nodo raíz fijo
        rootNode.attachChild(fixedRootNode); // Adjuntar el nuevo nodo raíz al nodo raíz principal

        
        sun = createPlanet("Sun", 2f, ColorRGBA.Yellow, Vector3f.ZERO);
        mercury = createPlanet("Mercury", 0.38f, ColorRGBA.Gray, new Vector3f(5f, 0, 0));
        venus = createPlanet("Venus", 0.72f, ColorRGBA.Brown, new Vector3f(8f, 0, 0));
        earth = createPlanet("Earth", 1f, ColorRGBA.Blue, new Vector3f(11f, 0, 0));
        mars = createPlanet("Mars", 0.53f, ColorRGBA.Red, new Vector3f(14f, 0, 0));
        jupiter = createPlanet("Jupiter", 1.2f, ColorRGBA.Orange, new Vector3f(20f, 0, 0));
        saturn = createPlanet("Saturn", 1f, ColorRGBA.Yellow, new Vector3f(30f, 0, 0));
        uranus = createPlanet("Uranus", 0.8f, ColorRGBA.Cyan, new Vector3f(40f, 0, 0));
        neptune = createPlanet("Neptune", 0.7f, ColorRGBA.Blue, new Vector3f(50f, 0, 0));

        fixedRootNode.attachChild(sun); // Adjuntar el sol al nuevo nodo raíz fijo
        fixedRootNode.attachChild(mercury); // Adjuntar planetas al nodo raíz fijo
        fixedRootNode.attachChild(venus);
        fixedRootNode.attachChild(earth);
        fixedRootNode.attachChild(mars);
        fixedRootNode.attachChild(jupiter);
        fixedRootNode.attachChild(saturn);
        fixedRootNode.attachChild(uranus);
        fixedRootNode.attachChild(neptune);
        
        // Crear la luna y adjuntarla al nodo de la tierra
        moon = createPlanet("Moon", 0.27f, ColorRGBA.Gray, new Vector3f(2.5f, 0, 0)); //posicion inicial de la luna
        earth.attachChild(moon); //adjuntar la luna al nodo de la tierra
        
        // Mover y orientar la cámara para que esté fija
        cam.setLocation(new Vector3f(0, 0, 20)); // Establecer la posición de la cámara
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y); // Orientar la cámara hacia el origen
        
    }

    private Node createPlanet(String name, float size, ColorRGBA color, Vector3f position) {
        //crear geometria del planeta
        Node planetNode = new Node(name);

        Box box = new Box(size, size, size);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        geometry.setMaterial(material);
        
        planetNode.attachChild(geometry); // crear nodo del planeta
        planetNode.setLocalTranslation(position); // configura la posision del planeta

        return planetNode;
    }

    @Override
    public void simpleUpdate(float tpf) {
        rotatePlanet(mercury, tpf, 50f, 5f); //velocidad para completar la orbita
        rotatePlanet(venus, tpf, 70f, 8f);
        rotatePlanet(earth, tpf, 90f, 10f);
        rotatePlanet(moon, tpf, 6f, 1f); 
        rotatePlanet(mars, tpf, 110f, 15f);
        rotatePlanet(jupiter, tpf, 150f, 20f);
        rotatePlanet(saturn, tpf, 190f, 25f);
        rotatePlanet(uranus, tpf, 230f, 28f);
        rotatePlanet(neptune, tpf, 270f, 30f);
    }



    private void rotatePlanet(Node planet, float tpf, float distance, float orbitalPeriod) {
        planet.rotate(0, tpf, 0); // El planeta rota sobre su propio eje
        
        // calcular la posicion orbital del planeta alrededor del sol
        float angle = tpf * FastMath.TWO_PI / orbitalPeriod;
        planet.rotate(0, angle, 0); // planeta rota alrededor del sol

        // Calcular la nueva posición del planeta en su órbita y su posicion
        Vector3f orbitPosition = planet.getLocalTranslation().subtract(sun.getLocalTranslation());
        Quaternion orbit = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
        orbitPosition = orbit.mult(orbitPosition);
        orbitPosition = orbitPosition.add(sun.getLocalTranslation());
        planet.setLocalTranslation(orbitPosition);
        
        if (planet.getName().equals("Neptune")) {
            planet.rotate(0, tpf, 0); // Neptuno rota sobre su propio eje en sentido horario
        } else {
            planet.rotate(0, -tpf, 0); // Los demás planetas rotan sobre su propio eje en sentido antihorario
        }
    }


    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
