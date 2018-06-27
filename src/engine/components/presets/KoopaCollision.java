package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.systems.collisions.CollisionDirection;

public class KoopaCollision extends Collidable {


    public KoopaCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Top, Actions.damage());

        this.setOnDirection(CollisionDirection.Bot, Actions.bounce(CollisionDirection.Bot, 40));

        this.setOnDirection(CollisionDirection.Left, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
            Actions.bounce(CollisionDirection.Left, 40).accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Right, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
            Actions.bounce(CollisionDirection.Right, 40).accept(e1, e2);
        });
    }
}
