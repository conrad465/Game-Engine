/**
 *  This is an example of what a component class could look like. The component here is the VelocityComponent, which defines an
 * entity that is capable of moving such as the player or an enemy. This component then consists of just the velocity vectors
 * associated with that entity, such that if one wanted to change the rate of speed of that entity, one could simply access its 
 * VelocityComponent and change the XVel or YVel, as they are public variables. The VelocityComponent here implements an empty Interface
 * Component, which more is a tag that allows the entities to add specific components to their component lists.
**/
public VelocityComponent implements Component {

    public double XVel; //X velcity associated with an entity that has this VelocityComponent
    public double YVel; //Y velcity associated with an entity that has this VelocityComponent

    /**
     * Construtor for a VelocityComponent, just giving it its XVel and YVel values to be stored. 
     * @param double XVel entity's initial XVel
     * @param double YVel entity's initial YVel
    **/
    public VelocityComponent (double XVel, double YVel) {
        this.XVel = XVel;
        this.YVel = YVel;
    }

}