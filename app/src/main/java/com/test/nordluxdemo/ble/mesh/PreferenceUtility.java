package com.test.nordluxdemo.ble.mesh;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceUtility {
//    private static final String MESH_NETWORK = "mesh_network";
//    private static final String MESH_DEVICE = "mesh_device";
    private static final String MESH_DATA = "mesh_data";

//    private static final String MESH_NETKEYINDEX_COUNTER = "netkeyindex_counter";
//    private static final String MESH_APPKEYINDEX_COUNTER = "appkeyindex_counter";
    private static final String MESH_UNICAST_ADDR_COUNTER = "unicast_addr_counter";
    private static final String MESH_VIRTUAL_ADDR_COUNTER = "virtual_addr_counter";
    private static final String MESH_GROUP_ADDR_COUNTER = "group_addr_counter";
    private static final String MESH_REFRESH_STATE = "refresh_state";
    private static final String MESH_NETKEY_INFO_LIST = "netkey_info_list";
    private static final String MESH_GROUP_INFO_LIST = "group_info_list";
    private static final String MESH_PD_INFO_LIST = "pd_info_list";
    private static final String MESH_IV_INDEX = "iv_index";
    private static final String MESH_FIRST_RUN_APP_STATUS = "first_run_app_status";


    public static void saveFirstRunAppStatus(Context context, boolean status){
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(MESH_FIRST_RUN_APP_STATUS, status);
        editor.commit();
    }

    public static boolean getFirstRunAppStatus(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        boolean ret = settings.getBoolean(MESH_FIRST_RUN_APP_STATUS, true);
        return ret;
    }

    public static void saveNetkeyList(Context context, ArrayList<MeshUtils.NETKEY_INFO> netkeyInfoList) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();

        Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.NETKEY_INFO.class).getType();
        Gson gson = new Gson();
        String json = gson.toJson(netkeyInfoList, listType);
        editor.putString(MESH_NETKEY_INFO_LIST, json);

        editor.commit();
    }

    public static ArrayList<MeshUtils.NETKEY_INFO> getNetkeyList(Context context) {
        ArrayList<MeshUtils.NETKEY_INFO> ret;
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        String json = settings.getString(MESH_NETKEY_INFO_LIST, null);
        if (json == null) {
            ret = new ArrayList<>();
        } else {
            Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.NETKEY_INFO.class).getType();
            Gson gson = new Gson();
            ret = gson.fromJson(json, listType);
        }
        if(ret.size() == 0) {
            ret.add(new MeshUtils.NETKEY_INFO((short)1, "Test_Network", MeshUtils.NET_KEY_1));
            ret.add(new MeshUtils.NETKEY_INFO((short)2, "Test_Network", MeshUtils.NET_KEY_2));
        }
        return ret;
    }

    public static void saveGroupList(Context context, ArrayList<MeshUtils.GROUP_INFO> groupInfoList) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();

        Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.GROUP_INFO.class).getType();
        Gson gson = new Gson();
        String json = gson.toJson(groupInfoList, listType);
        editor.putString(MESH_GROUP_INFO_LIST, json);

        editor.commit();
    }

    public static ArrayList<MeshUtils.GROUP_INFO> getGroupList(Context context) {
        ArrayList<MeshUtils.GROUP_INFO> ret;
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        String json = settings.getString(MESH_GROUP_INFO_LIST, null);
        if (json == null) {
            ret = new ArrayList<>();
        } else {
            Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.GROUP_INFO.class).getType();
            Gson gson = new Gson();
            ret = gson.fromJson(json, listType);
        }
        return ret;
    }

    public static void saveMeshIvIndex(Context context, int iv_index) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MESH_IV_INDEX, iv_index);
        editor.commit();
    }

    public static int getMeshIvIndex(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        return settings.getInt(MESH_IV_INDEX, 0);
    }

    public static void saveClientAddrCounter(Context context, int addr_counter, int type) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        switch(type) {
            case MeshUtils.AddrType.UNICAST:
                editor.putInt(MESH_UNICAST_ADDR_COUNTER, addr_counter);
                break;
            case MeshUtils.AddrType.GROUP:
                editor.putInt(MESH_GROUP_ADDR_COUNTER, addr_counter);
                break;
            case MeshUtils.AddrType.VIRTUAL:
                editor.putInt(MESH_VIRTUAL_ADDR_COUNTER, addr_counter);
                break;
        }
        editor.commit();
    }

    public static int getClientAddrCounter(Context context, int type) {
        /** address type rule
         * Unassigned address: 0b0000000000000000
         * Unicast address: 0b0xxxxxxxxxxxxxxx, 0x0001~0x7FFF
         * Virtual address: 0b10xxxxxxxxxxxxxx, 0x8000~0xBFFF
         * Group address: 0b11xxxxxxxxxxxxxx, 0xC000~0xFFFF
         */
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        switch(type) {
            case MeshUtils.AddrType.UNICAST:
                return settings.getInt(MESH_UNICAST_ADDR_COUNTER, 1);
            case MeshUtils.AddrType.GROUP:
                return settings.getInt(MESH_GROUP_ADDR_COUNTER, 0xC000);
            case MeshUtils.AddrType.VIRTUAL:
                return settings.getInt(MESH_VIRTUAL_ADDR_COUNTER, 0x8000);
        }
        return 0;
    }

    public static void setRefreshState(Context context, byte state) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MESH_REFRESH_STATE, state);
        editor.commit();
    }

    public static void savePdList(Context context, ArrayList<MeshUtils.PD_INFO> pdInfoList) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();

        Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.PD_INFO.class).getType();
        Gson gson = new Gson();
        String json = gson.toJson(pdInfoList, listType);
        editor.putString(MESH_PD_INFO_LIST, json);

        editor.commit();
    }

    public static ArrayList<MeshUtils.PD_INFO> getPdList(Context context) {
        ArrayList<MeshUtils.PD_INFO> ret;
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        String json = settings.getString(MESH_PD_INFO_LIST, null);
        if (json == null) {
            ret = new ArrayList<>();
        } else {
            Type listType = TypeToken.getParameterized(ArrayList.class, MeshUtils.PD_INFO.class).getType();
            Gson gson = new Gson();
            ret = gson.fromJson(json, listType);
        }
        return ret;
    }

    public static void resetMeshData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MESH_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
