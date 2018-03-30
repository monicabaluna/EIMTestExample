package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by monica on 30.03.2018.
 */
public interface Constants {

    final public static String TAG = "[My Service]";

    final public static int MIN_CLICKS = 6;
    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;

    final public static ArrayList<String> actionTypes = new ArrayList<>(Arrays.asList("act1", "act2", "act3"));

}
