package com.airoha.libmesh.core;

import android.bluetooth.BluetoothGatt;

/**
 * Callbacks for the actions taken by AirohaLink
 *
 * @see AirohaLink
 */

public interface OnAirohaEventListener {
    /**
     * Indicating GATT Connected. SDK user can handle the followup by overriding this.
     */
    void OnGattConnected(BluetoothGatt gatt);

    /**
     * Indicating for GATT Disconnected. SDK user can handle the followup by overriding this.
     */
    void OnGattDisconnected(BluetoothGatt gatt);

    /**
     * Callback for getting the Working Areas information. SDK user should use this to do some fool-proof handling.
     *
     * @param workingArea,  the number of the area that is working. It should be 1 or 2. You cannot update the area that is working.
     * @param area1Rev,     the FW version of Area 1
     * @param isArea1Valid, is Area 1 valid for update
     * @param area2Rev,     the FW version of Area 2
     * @param isArea2Valid, is Area 2 valid for update
     */
    void OnWorkingAreaStatus(String workingArea, String area1Rev, boolean isArea1Valid, String area2Rev, boolean isArea2Valid);

    /**
     * Indicating the progress when updating the area.
     *
     * @param progress, 0~1
     */
    void OnUpdateProgrammingProgress(float progress);

    /**
     * Indicating the Working Area has been changed. BLE device will be shut down and need a reset.
     */
    void OnWorkingAreaChanged();

    void OnHandleBootCodeNotMatching();

    void OnHandleCodeAreaAddrNotMatching();

    void OnRequestMtuChangeStatus(boolean isAccepted);

    void OnNewMtu(int mtu);

    /**
     * Some reason that FW disable the OTA path. For ex: low-power state
     */
    void OnHandleOtaDisabled();

    void OnBinFileParseException();

}
