package engine.setup;

import java.util.*;

/**
 * Class which renders entities in and out of being acted upon based in a center location and rendering distance
 *
 * @author cndracos
 */
public class RenderManager {

    private final double renderDistance;
    private double centerX, centerY;

    private List<Integer> swapList = new ArrayList<>();
    private Map<Integer, Position> withinRender = new HashMap<>(), outsideRender = new HashMap<>();

    /**
     * Loads in a render distance and initial centers
     * @param renderDistance how far the program looks for other entities
     * @param initialCenterX where to initially start looking from in the X position
     * @param initialCenterY where to initially start looking from in the Y position
     */
    public RenderManager (double renderDistance, double initialCenterX, double initialCenterY) {
        this.renderDistance = renderDistance;
        centerX = initialCenterX; centerY = initialCenterY;
    }

    /**
     * Adds a new position to the system and loads it into outsideRender or insideRender
     * @param p new entity's position component
     */
    public void add (Position p) {
        if (withinRenderDistance(p.getXPos(), p.getYPos())) withinRender.put(p.getPID(), p);
        else outsideRender.put(p.getPID(), p);
    }


    /**
     * The main method called by the game loop, renders&garbage collects
     * @param newCenterX the player's current x center
     * @param newCenterY the player's current y center
     * @return set of id's which are within the render
     */
    public Set<Integer> render(double newCenterX, double newCenterY) {
        centerX = newCenterX; centerY = newCenterY;
        garbageCollect(); renderObjects();
        return withinRender.keySet();
    }

    public Set<Integer> render() {
        render(centerX, centerY);
        return withinRender.keySet();
    }

    private void garbageCollect() {
        updateNodes(withinRender, outsideRender, false);
    }

    private void renderObjects() {
        updateNodes(outsideRender, withinRender, true);
    }

    /**
     * Swaps out nodes to/from within/outside render, uses an iterator to search positions then checks
     * the boolean with withinRenderDistance helper method
     *
     * @param origin The render group (outside or inside) that the positions currently reside
     * @param destination The (potential) new render group if it needs to be swapped
     * @param intoRender dictates whether or not we are looking to go into or out of render
     */
    private void updateNodes (Map<Integer, Position> origin, Map<Integer, Position> destination, boolean intoRender) {
        for (Iterator<Position> iterator = origin.values().iterator(); iterator.hasNext(); ) {
            Position p = iterator.next();
            if (withinRenderDistance(p.getXPos(), p.getYPos()) == intoRender) {
                destination.put(p.getPID(), p);
                swapList.add(p.getPID()); //has to store nodes to get listed, cannot do dynamically
            }
        }
        removeOldNodes(origin);
    }


    private void removeOldNodes (Map<Integer, Position> updated) {
        for (int i : swapList) {
            updated.remove(i);
        }
        swapList.clear();
    }

    private boolean withinRenderDistance(double x, double y) {
        return true;//Math.abs(centerX - x) < renderDistance && Math.abs(centerY - y) < renderDistance;
    }
}
