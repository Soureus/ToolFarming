package sour.toolfarming.item.tools;

import java.util.ArrayList;

public class LevelingToolsData {

    //LARYN
    public static final ArrayList<Float> LARYN_SWORD_DAMAGES = registerFloatLevels(2.5f,3f,4.5f);
    public static final ArrayList<Float> LARYN_SWORD_SPEEDS = registerFloatLevels(-2.5f,-2.25f,-2.15f);
    public static final ArrayList<Float> LARYN_SWORD_EXP = registerFloatLevels(150f, 300f);
    public static final int LARYN_TOOL_LEVELS = 3;

    public static final ArrayList<Float> LARYN_PICKAXE_DAMAGES = registerFloatLevels(1f, 1f, 2f);
    public static final ArrayList<Float> LARYN_PICKAXE_ATTACK_SPEEDS = registerFloatLevels(-2.8f, -2.8f, -2.5f);
    public static final ArrayList<Float> LARYN_PICKAXE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> LARYN_AXE_DAMAGES = registerFloatLevels(4.5f, 5f, 6f);
    public static final ArrayList<Float> LARYN_AXE_ATTACK_SPEEDS = registerFloatLevels(-3.2f, -3.1f, -2.9f);
    public static final ArrayList<Float> LARYN_AXE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> LARYN_SHOVEL_DAMAGES = registerFloatLevels(1.5f, 1.5f, 2f);
    public static final ArrayList<Float> LARYN_SHOVEL_ATTACK_SPEEDS = registerFloatLevels(-3f, -3f, -2.7f);
    public static final ArrayList<Float> LARYN_SHOVEL_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> LARYN_HOE_DAMAGES = registerFloatLevels(-2f, -2f, -1f);
    public static final ArrayList<Float> LARYN_HOE_ATTACK_SPEEDS = registerFloatLevels(-1f, -1f, -0.5f);
    public static final ArrayList<Float> LARYN_HOE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    //ZAON
    public static final int ZAON_TOOL_LEVELS = 4;
    public static final ArrayList<Float> ZAON_EXP = registerFloatLevels(150f, 350f, 650f);

    public static final ArrayList<Float> ZAON_SWORD_DAMAGES = registerFloatLevels(2.8f,3.5f,4.5f);
    public static final ArrayList<Float> ZAON_SWORD_SPEEDS = registerFloatLevels(-2.5f,-2.25f,-2f);
    public static final ArrayList<Float> ZAON_SWORD_EXP = registerFloatLevels(150f, 300f);

    public static final ArrayList<Float> ZAON_PICKAXE_DAMAGES = registerFloatLevels(1f, 1f, 2f);
    public static final ArrayList<Float> ZAON_PICKAXE_ATTACK_SPEEDS = registerFloatLevels(-2.8f, -2.8f, -2.5f);
    public static final ArrayList<Float> ZAON_PICKAXE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> ZAON_AXE_DAMAGES = registerFloatLevels(4.5f, 5f, 6f);
    public static final ArrayList<Float> ZAON_AXE_ATTACK_SPEEDS = registerFloatLevels(-3.3f, -3.1f, -2.8f);
    public static final ArrayList<Float> ZAON_AXE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> ZAON_SHOVEL_DAMAGES = registerFloatLevels(1.5f, 1.5f, 2f);
    public static final ArrayList<Float> ZAON_SHOVEL_ATTACK_SPEEDS = registerFloatLevels(-3f, -3f, -2.7f);
    public static final ArrayList<Float> ZAON_SHOVEL_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    public static final ArrayList<Float> ZAON_HOE_DAMAGES = registerFloatLevels(-2f, -2f, -1f);
    public static final ArrayList<Float> ZAON_HOE_ATTACK_SPEEDS = registerFloatLevels(-1f, -1f, -0.5f);
    public static final ArrayList<Float> ZAON_HOE_MINING_SPEEDS = registerFloatLevels(-0.5f, 0.5f, 2f);

    private static ArrayList<Float>  registerFloatLevels(float ... lvl){
        ArrayList<Float> registered = new ArrayList<>();

        for (float i : lvl){
            registered.add(i);
        }

        return registered;
    }


}
