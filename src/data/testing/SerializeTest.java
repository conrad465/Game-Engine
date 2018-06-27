package data.testing;

import authoring.gamestate.Level;
import data.DataGameState;
import data.DataWrite;
import engine.components.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerializeTest {

    public static void main(String[] args)
    {
        Level level1 = new Level(1);
        Level level2 = new Level(2);

        Map<Integer, Map<String, Component>> level1State = new HashMap<>();
        Map<Integer, Map<String, Component>> level2State = new HashMap<>();

        Integer entity1 = 1;
        Integer entity2 = 2;

        Map<String, Component> entity1Components = new HashMap<>();
        Map<String, Component> entity2Components = new HashMap<>();

        ArrayList<String> params = new ArrayList<>();

        params.add("1");
        params.add("3");

        XAcceleration entity1AccelerationX = new XAcceleration(1,1);
        YAcceleration entity1AccelerationY = new YAcceleration(1,0);
        entity1Components.put("XAcceleration", entity1AccelerationX);
        entity1Components.put("YAcceleration", entity1AccelerationY);



        params.clear();
        params.add("10");
        params.add("30");

        XPosition entity1PositionX = new XPosition(1,10);
        YPosition entity1PositionY = new YPosition(1,30);

        entity1Components.put("XPosition", entity1PositionX);
        entity1Components.put("YPosition", entity1PositionY);
        
        params.clear();
        params.add("5");
        params.add("0");

        XVelocity entity1VelocityX = new XVelocity(1,5);
        YVelocity entity1VelocityY = new YVelocity(1,0);

        entity1Components.put("XVelocity", entity1VelocityX);
        entity1Components.put("YVelocity", entity1VelocityY);

        params.clear();
        params.add("Mario.png");

        try {
            Sprite entity1Sprite = new Sprite(1, "Mario.png");
            entity1Components.put("Sprite", entity1Sprite);
        } catch (FileNotFoundException e) {


        }

        params.clear();
        params.add("4");
        params.add("10");
//__________________________________________________
        XAcceleration entity2AccelerationX = new XAcceleration(2,4);
        YAcceleration entity2AccelerationY = new YAcceleration(2,10);

        entity2Components.put("XAcceleration", entity2AccelerationX);
        entity2Components.put("YAcceleration", entity2AccelerationY);




        params.clear();
        params.add("100");
        params.add("50");

        XPosition entity2PositionX = new XPosition(2, 0);
        YPosition entity2PositionY = new YPosition(2, 0);

        entity2Components.put(XPosition.KEY, entity2PositionX);
        entity2Components.put(YPosition.KEY, entity2PositionY);


        Width w = new Width (2, 20);
        Height h = new Height(2, 100);
        entity2Components.put(Width.KEY, w);
        entity2Components.put(Height.KEY, h);


        params.clear();
        params.add("1");
        params.add("-1");

        XVelocity entity2VelocityX = new XVelocity(2, 20);
        YVelocity entity2VelocityY = new YVelocity(2, 0);
        entity2Components.put("XVelocity", entity2VelocityX);
        entity2Components.put("YVelocity", entity2VelocityY);



        params.clear();
        params.add("games/DemoDemo/data/images/mario.png");

        try {
            Sprite entity2Sprite = new Sprite(2, "Mario.png");
            entity2Components.put("Sprite", entity2Sprite);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        level1State.put(1,entity1Components);
        level2State.put(2, entity2Components);



        Map<Level,Map<Integer,Map<String,Component>>> testState = new HashMap<>();
        testState.put(level1,level1State);
        testState.put(level2,level2State);
        DataGameState state = new DataGameState(testState, "Files!");

        File xml = new File("sdf");
        try {
            DataWrite.saveFile(state);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

