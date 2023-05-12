package sour.toolfarming.item.tools;

import java.util.ArrayList;

public class LevelingToolsData {

    public static final ArrayList<Float> LARYN_SWORD_DAMAGES = registerFloatLevels(2f,2.5f,3.5f);
    public static final ArrayList<Float> LARYN_SWORD_SPEEDS = registerFloatLevels(-2.5f,-2.25f,-2f);
    public static final ArrayList<Float> LARYN_SWORD_EXP = registerFloatLevels(150f, 300f);
    public static final int LARYN_TOOL_LEVELS = 3;

    public static final ArrayList<Float> LARYN_PICKAXE_DAMAGES = registerFloatLevels(3f, 3f, 3f);
    public static final ArrayList<Float> LARYN_PICKAXE_ATTACK_SPEEDS = registerFloatLevels(-2.8f, -2.8f, -2.8f);
    public static final ArrayList<Float> LARYN_PICKAXE_MINING_SPEEDS = registerFloatLevels(0f, 1f, 2f);

    private static ArrayList<Float>  registerFloatLevels(float ... lvl){
        ArrayList<Float> registered = new ArrayList<>();

        for (float i : lvl){
            registered.add(i);
        }

        return registered;
    }


}
