package com.test.nordluxdemo.ble.mesh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.nordluxdemo.R;

import java.util.ArrayList;

public class MeshDeviceListActivity extends AppCompatActivity {
    private final static String TAG = "Airoha_" + MeshDeviceListActivity.class.getSimpleName();
    private MeshDeviceListAdapter mMeshDeviceListAdapter;
    private Short mNetkeyIndex;
    private String mGroupAddr;
    private ListView mMeshListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("Mesh Device List");
        setContentView(R.layout.mesh_list_activity);

        mMeshListView = findViewById(R.id.mesh_list);
        mMeshDeviceListAdapter = new MeshDeviceListAdapter();
        mMeshListView.setAdapter(mMeshDeviceListAdapter);

        final Intent intent = getIntent();
        mNetkeyIndex = intent.getShortExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, (short) 0);
        mGroupAddr = intent.getStringExtra(MeshUtils.EXTRAS_GROUP_ADDR);
    }

    @Override
    protected void onPause() {
        mMeshDeviceListAdapter.clear();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MeshUtils.gPdInfoList = PreferenceUtility.getPdList(getApplicationContext());

        mMeshDeviceListAdapter = new MeshDeviceListAdapter();
        mMeshListView.setAdapter(mMeshDeviceListAdapter);

        for (MeshUtils.PD_INFO pdInfo : MeshUtils.gPdInfoList) {
            if (mGroupAddr != null && !pdInfo.mModelGroupMap.values().contains(mGroupAddr)) {
                continue;
            }
            if (!pdInfo.mNekworkIndexArray.contains(mNetkeyIndex)) {
                continue;
            }
            mMeshDeviceListAdapter.addDevice(pdInfo);
            Log.d(TAG, "BdAddr:" + pdInfo.mDeviceBdAddr + ", Primary Addr:" + MeshUtils.getFullAddrStr(pdInfo.mUnicastAddr));
        }
    }

    // Adapter for holding devices found through scanning.
    private class MeshDeviceListAdapter extends BaseAdapter {
        private ArrayList<MeshUtils.PD_INFO> mMeshDevices;
        private LayoutInflater mInflator;

        public MeshDeviceListAdapter() {
            super();
            mMeshDevices = new ArrayList<>();
            mInflator = MeshDeviceListActivity.this.getLayoutInflater();
        }

        public void addDevice(MeshUtils.PD_INFO pd_info) {
            if (!mMeshDevices.contains(pd_info)) {
                mMeshDevices.add(pd_info);
            }
        }

        public void clear() {
            mMeshDevices.clear();
        }

        @Override
        public int getCount() {
            return mMeshDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mMeshDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            OnConfigClick btnConfiglistener;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_mesh_pdevice, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceName = (TextView) view.findViewById(R.id.textView_pdevice_device_name);
                viewHolder.deviceBdAddr = (TextView) view.findViewById(R.id.textView_pdevice_bdAddr);
                viewHolder.unicastAddr = (TextView) view.findViewById(R.id.textView_pdevice_uniAddr);
                viewHolder.btnConfig = (Button) view.findViewById(R.id.btn_pdevice_Config);

                btnConfiglistener = new OnConfigClick();
                viewHolder.btnConfig.setOnClickListener(btnConfiglistener);

                view.setTag(viewHolder);
                view.setTag(viewHolder.btnConfig.getId(), btnConfiglistener);
            } else {
                viewHolder = (ViewHolder) view.getTag();
                btnConfiglistener = (OnConfigClick) view.getTag(viewHolder.btnConfig.getId());
            }

            MeshUtils.PD_INFO device = mMeshDevices.get(position);
            viewHolder.deviceName.setText(device.mDeviceName == null ? "Unknown Device":device.mDeviceName);
            viewHolder.deviceBdAddr.setText(device.mDeviceBdAddr);
            viewHolder.unicastAddr.setText(MeshUtils.shortToHexString(device.mUnicastAddr));

            btnConfiglistener.setPosition(position);


            return view;
        }

        class OnConfigClick implements View.OnClickListener {
            int position;

            public void setPosition(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                MeshUtils.PD_INFO pdInfo = mMeshDevices.get(position);
                final Intent intent = new Intent(MeshDeviceListActivity.this, MeshDeviceInfoActivity.class);
                intent.putExtra(MeshUtils.EXTRAS_BT_NAME, pdInfo.mDeviceName);
                intent.putExtra(MeshUtils.EXTRAS_BT_BD_ADDR, pdInfo.mDeviceBdAddr);
                intent.putExtra(MeshUtils.EXTRAS_DEVICE_UUID, pdInfo.mDeviceUUID);
                intent.putExtra(MeshUtils.EXTRAS_UNICAST_ADDR, pdInfo.mUnicastAddr);
                intent.putExtra(MeshUtils.EXTRAS_DEVICE_KEY, pdInfo.mDeviceKey);
                intent.putExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, mNetkeyIndex);

                startActivity(intent);
            }
        }

        class ViewHolder {
            TextView deviceName;
            TextView deviceBdAddr;
            TextView unicastAddr;
            Button btnConfig;
        }
    }
}
