package sour.toolfarming.item.tools;

import java.util.ArrayList;

public class LevelingToolsData {

    public static final ArrayList<Float> LARYN_SWORD_DAMAGES = registerFloatLevels(1f,1.5f,2f);
    public static final ArrayList<Float> LARYN_SWORD_SPEEDS = registerFloatLevels(2f,2.5f,3f);
    public static final ArrayList<Float> LARYN_SWORD_EXP = registerFloatLevels(150f, 300f);
    public static final int LARYN_TOOL_LEVELS = 3;

    private static ArrayList<Float>  registerFloatLevels(float ... lvl){
        ArrayList<Float> registered = new ArrayList<>();

        for (float i : lvl){
            registered.add(i);
        }

        return registered;
    }


}
