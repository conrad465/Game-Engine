package engine.actions;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * This is the class that the authoring will call to assign an action to an entity when adding either keyInput
 * or AI components. It uses reflection to find the action that is specified from an 'actions' class and return
 * that to the caller.
 *
 * @author cndracos
 *
 */
public class ActionReader {
    private String methodName;
    private Method method;
    private Class<?>[] methodParams;
    private Object[] invokeArgs;

    // Scott changed this to suppress errors
    private static Actions actions = new Actions();

    /**
     * This is the method which gets the action by having a series of private method calls
     * which reads the parameters to return an action (Consumer)
     *
     * @param methodName Name of the method in the action class aka the action
     * @param arguments The arguments that the action would need in order to execute
     * @return the action specified in the form of a Consumer
     */
    public Object getAction(String methodName, List<Object> arguments) {
        this.methodName = methodName;
        methodParams = getMethodParams(actions.getClass());

        if (methodParams.length != arguments.size()) {
            System.out.println("Improper argument count");
            return null;
        }

        try {
            method = actions.getClass().getMethod(this.methodName, methodParams);
        } catch (NoSuchMethodException e) {
            System.out.println("The method you called does not exist");
        }

        invokeArgs = getInvokeArguments(arguments);
        try {
            try {
                return (Consumer<?>) method.invoke(actions, invokeArgs); //finally invokes the appropriate method with the given arguments and returns the Consumer
            } catch (ClassCastException e) {
                return (BiConsumer<?,?>) method.invoke(actions, invokeArgs); //finally invokes the appropriate method with the given arguments and returns the Consumer
            }
        }
        catch (IllegalAccessException e) {
            System.out.println("I don't know what this exception is catching");
        } catch (InvocationTargetException e) {
            System.out.println("Same with this one");
        } catch (IllegalArgumentException e) {
            System.out.println("The method you are invoking does not use these parameters");
        }
        return null;
    }



    /**
     * Takes in a class type and returns the parameters that a given method in that class needs
     * @param clazz the Action class as a Class object
     * @return array of Class object which specify the parameters of the method being looked for
     */
    private Class<?>[] getMethodParams (Class<?> clazz) {
        methodParams = new Class[0];
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method declaredMethod: declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                methodParams = declaredMethod.getParameterTypes();
            }
        }
        return methodParams;
    }


    /**
     * Creates the arguments needed to be passed to the method
     * @param arguments list of arguments
     * @return array of objects which are parameters to be passed in a method call
     */
    private Object[] getInvokeArguments(List<Object> arguments) {
        invokeArgs = new Object[methodParams.length];
        for (int i = 0; i < methodParams.length; i++) {
            invokeArgs[i] = arguments.get(i);
        }
        return invokeArgs;
    }
}
