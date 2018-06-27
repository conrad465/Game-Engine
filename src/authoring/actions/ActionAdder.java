package authoring.actions;

import authoring.entities.Entity;
import data.DataUtils;
import engine.actions.ActionReader;
import engine.actions.Actions;
import engine.components.Component;
import engine.systems.collisions.CollisionDirection;
import javafx.scene.input.KeyCode;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Author @Conrad This classss provides the backend utility for
    creating actions and pairing them with components that
    require actions. This class interacts with ActionAdderView and
    ActionReader/Actions in engine and allows the user to create
    consumers
 **/
public class ActionAdder {

    private Entity entity;
    private Map<String, List<String>> methodParams;
    private Supplier input;
    private List<Supplier> suppliers;
    private static final String AI = "AI";
    private static final String COLLIDABLE = "Collidable";
    private static final String KEYCODE = "KeyInput";
    private static final String FAILED = "Couldnt make action";
    private static final String COMMA = ",";
    private static final String ACTTION_ERROR_START = "Action not supported: you need to add ";
    private static final String ACTION_ERROR_END ="for this action combination";
    private static final String ACTION_PROPS = "authoring.actions/Actions";
    ResourceBundle cNecess  = ResourceBundle.getBundle(ACTION_PROPS);


    ActionAdder(Entity entity){
        this.entity=entity;
        initParams();
    }

    public Map<String, List<String>> getActions(){
        /* returns the map of parameters
         */
       return methodParams;
    }

    private void initParams(){
        /*reflexively builds the map of what objects each method in action needs
          to create the desired consumer action
         */
        methodParams = new HashMap<>();
        for(Method method : Actions.class.getMethods()){
            if(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                List<String> parameters = new ArrayList<>();
                for (Type param : Arrays.asList(method.getGenericParameterTypes())) {
                    parameters.add(param.getTypeName());
                }
                methodParams.put(method.getName(), parameters);
            }
        }
    }

    public void buildConsumer(String actionName, String component){
        /*creates suppliers which are popup boxes that prompt the user
         *for parameters required by Actions for a method selected by the user
         */
        if(checkAction(actionName,component)) {
            suppliers = new ArrayList<>();
            List<String> params = methodParams.get(actionName);
            for (String param : params) {
                System.out.println(param);
                suppliers.add(SupplierFactory.makeSupplier(param, entity));
            }
            input = SupplierFactory.makeInput(component, entity);
        }
    }

    public void buildActionComponent(String methodName, String component){
      /* method builds the parameter list of objects which are inputs to the actions
         class, makes a call to ActionReader with the method and paramters to get the consumer
         for that action and configures the selected component with that action
       */
        try {
          List<Object> args = new ArrayList<>();
          ActionReader aRead = new ActionReader();
          for (Supplier supplier : suppliers)
              args.add(supplier.getData());
          System.out.print("Supplier with " + args.size() + " items");
          if (component.equals(AI))
             configureAI((Consumer)aRead.getAction(methodName,args));
          if (component.equals(COLLIDABLE))
              configureCollidable((BiConsumer)aRead.getAction(methodName, args));
          if(component.equals(KEYCODE))
              configureKeyInput((Consumer)aRead.getAction(methodName,args));
      }
      catch(Exception e){
          DataUtils.ErrorStatement(FAILED);
          System.out.print("Error loading in ine 92 ActionAdder");
          e.printStackTrace();
      }
    }

    private void configureAI(Consumer action){
        ((engine.components.AI) entity.get(AI)).setAction(action);
    }

    private void configureCollidable(BiConsumer action){
        ((engine.components.Collidable) entity.get(COLLIDABLE)).setOnDirection((CollisionDirection) input.getData(),action);
    }

    private void configureKeyInput(Consumer action){
        ((engine.components.KeyInput) entity.get(KEYCODE)).addCode((KeyCode) input.getData(),action);
    }

    private List<Object> reverse(List<Object> array){
        Object temp;
        for(int a =0 ; a<array.size()/2; a++){
            temp = array.get(a);
            array.set(a,array.get(array.size()-1-a));
            array.set(array.size()-1-a, temp);
        }
        return array;
    }

    private boolean checkAction(String action, String component){
        String error = ACTTION_ERROR_START;
        cNecess  = ResourceBundle.getBundle(ACTION_PROPS);
        String[] f = cNecess.getString(action).split(COMMA);
        boolean works =true;
        System.out.print("Entity has ");
        for(Component c : entity.getComponentList()){
            System.out.print(c.getKey()+" ");
        }
        for(String c : f){
            System.out.println("Trying to find " + c);
            if(entity.get(c)==null) {
                System.out.print("Failed");
                error = error + c + " ";
                works=false;
            }
        }
        if(!works){
            DataUtils.ErrorStatement(error + ACTION_ERROR_END);
        }
        return works;
    }

}
