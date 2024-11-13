package net.grimm.eternity.common.network;

import net.grimm.eternity.common.data.GlobalData;

public abstract class SyncMessage<T extends GlobalData<T, U>, U extends SyncMessage<T, U>> implements IPacket<U> {

    protected final T data;

    public SyncMessage(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
