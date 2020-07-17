package com.test.nordluxdemo.ble.mesh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.nordluxdemo.R;

import java.util.ArrayList;

public class MeshNetworkListActivity extends BaseActivity {
    private final static String TAG = "Airoha_" + MeshNetworkListActivity.class.getSimpleName();
    protected Intent mServiceIntent = null;
    protected BluetoothLeService mBluetoothLeService = null;
    private NetworkListAdapter mNetworkListAdapter;
    private ListView mMeshListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_list_activity);
        //getSupportActionBar().setTitle("Mesh Network List");
        mMeshListView = findViewById(R.id.mesh_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        menu.findItem(R.id.menu_add).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    void refreshList() {
        mNetworkListAdapter = new NetworkListAdapter();
        mMeshListView.setAdapter(mNetworkListAdapter);
        mNetworkListAdapter.addNetwork(MeshUtils.NET_KEY_INDEX, MeshUtils.NET_NAME, MeshUtils.NET_KEY);

        for (MeshUtils.NETKEY_INFO info : MeshUtils.gNetkeyInfoList) {
            mNetworkListAdapter.addNetwork(info.mNetkworkIndex, info.mNetworkName, info.mNetKey);
        }
    }


    // Adapter for holding devices found through scanning.
    private class NetworkListAdapter extends BaseAdapter {
        private ArrayList<MeshNetworkInfo> mMeshNetworks;
        private LayoutInflater mInflator;

        public NetworkListAdapter() {
            super();
            mMeshNetworks = new ArrayList<>();
            mInflator = MeshNetworkListActivity.this.getLayoutInflater();
        }

        public void addNetwork(short index, String name, byte[] key) {
            for (MeshNetworkInfo tmp:mMeshNetworks) {
                if (tmp.mName.equals(name)) {
                    return;
                }
            }

            MeshNetworkInfo meshNetworkInfo = new MeshNetworkInfo();
            meshNetworkInfo.mIndex = index;
            meshNetworkInfo.mName = name;
            meshNetworkInfo.mKey = key;
            mMeshNetworks.add(meshNetworkInfo);
        }

        public MeshNetworkInfo getNetworkInfo(int position) {
            return mMeshNetworks.get(position);
        }

        public void clear() {
            mMeshNetworks.clear();
        }

        @Override
        public int getCount() {
            return mMeshNetworks.size();
        }

        @Override
        public Object getItem(int i) {
            return mMeshNetworks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            OnDevicesClick btnDeviceslistener;
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_mesh_network, null);
                viewHolder = new ViewHolder();
                viewHolder.networkIndex = (TextView) view.findViewById(R.id.textView_network_id);
                viewHolder.networkName = (TextView) view.findViewById(R.id.textView_network_name);
                viewHolder.networkKey = (TextView) view.findViewById(R.id.textView_network_key);
                viewHolder.btnDevices = (Button) view.findViewById(R.id.btn_MeshNetworkDevices);

                btnDeviceslistener = new OnDevicesClick();
                viewHolder.btnDevices.setOnClickListener(btnDeviceslistener);

                view.setTag(viewHolder);
                view.setTag(viewHolder.btnDevices.getId(), btnDeviceslistener);
            } else {
                viewHolder = (ViewHolder) view.getTag();
                btnDeviceslistener = (OnDevicesClick) view.getTag(viewHolder.btnDevices.getId());
            }

            MeshNetworkInfo networkInfo = mMeshNetworks.get(position);
            viewHolder.networkIndex.setText(String.valueOf(networkInfo.mIndex));
            viewHolder.networkName.setText(networkInfo.mName);
            viewHolder.networkKey.setText(MeshUtils.bytesToHexString(networkInfo.mKey));

            btnDeviceslistener.setPosition(position);



            viewHolder.btnDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout layout_0 = (RelativeLayout) v.getParent().getParent();
                    LinearLayout layout_1 = (LinearLayout) layout_0.findViewById(R.id.layout_network_LeftArea);
                    TextView tv_index = (TextView) layout_1.findViewById(R.id.textView_network_id);
                    MeshUtils.NETKEY_INFO netkeyInfoInfo = MeshUtils.getNetkeyInfo(Short.valueOf(tv_index.getText().toString()));
                    Short netkeyIndex = MeshUtils.NET_KEY_INDEX;
                    if (netkeyInfoInfo != null) {
                        netkeyIndex = netkeyInfoInfo.mNetkworkIndex;
                    }
                    final Intent intent = new Intent(MeshNetworkListActivity.this, MeshDeviceListActivity.class);
                    intent.putExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, netkeyIndex);
                    startActivity(intent);
                }
            });


            return view;
        }



        class OnDevicesClick implements View.OnClickListener {
            int position;

            public void setPosition(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                MeshNetworkInfo networkInfo = mMeshNetworks.get(position);
                Short netkeyIndex = MeshUtils.NET_KEY_INDEX;
                if (networkInfo != null) {
                    netkeyIndex = networkInfo.mIndex;
                }
                final Intent intent = new Intent(MeshNetworkListActivity.this, MeshDeviceListActivity.class);
                intent.putExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, netkeyIndex);
                startActivity(intent);
            }
        }

        public class MeshNetworkInfo {
            public short mIndex;
            public String mName;
            public byte[] mKey;
        }

        class ViewHolder {
            TextView networkIndex;
            TextView networkName;
            TextView networkKey;
            Button btnGroups;
            Button btnDevices;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if(mBluetoothLeService != null) {
            mBluetoothLeService.getAirohaMeshMgr().destroy();
        }
        finish();
    }
}
