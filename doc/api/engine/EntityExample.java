/**
 * This is an example of what our entity class could look like. This is the most basic type of entity which could be 
 * implemented, as it only stores a double and then its components. If we decide that all entities should have an X and
 * Y coordinate, we may decide to add that as well, but this current implementation assumes that a component would instead
 * hold these values, if one were needed. Note that no setters or getters are needed in this class, as its instance variables
 * are public.
**/
public Entity {

    public double ID; //unique ID to an entity
    public List<Component> components; //list of components which define the entity

    /**
     * The constructor simply sets the ID of the entity and initializes its list of components
     * @param double ID which identifies an entity
    **/
    public Entity (double ID) {
        this.ID = ID;
        components = new ArrayList<Component>();
    }

}