package org.hexlet.gamexo.ai.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2212
 * <br>Prints (or not prints) working logs into IDE console</br>
 */
public class LoggerAI {

    private static boolean printAllowed = true;// print executes
//    private static boolean printAllowed = false;// print doesn't execute

    public static void p(String message) {
        if (printAllowed)
            System.out.println(message);
    }

}
