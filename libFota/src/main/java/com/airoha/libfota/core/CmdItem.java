package com.airoha.libfota.core;

class CmdItem {
    public byte[] Cmd;
    public boolean isRespSuccess = false;
    public boolean isSend = false;
    public int RetryCount = 0;
}
