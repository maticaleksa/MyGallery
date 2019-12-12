package com.example.picture_taking_module;

public class PictureTaking {
    private static volatile PictureTaking instance;
    private PictureTakingComponent pictureTakingComponent;

    private PictureTaking() {}

    public static void setInstance(PictureTakingComponent pictureTakingComponent) {
        if (instance == null) {
            synchronized (PictureTaking.class) {
                if (instance == null) {
                    instance = new PictureTaking();
                }
            }
        } else {
            throw new IllegalStateException("PictureTaking.setInstance must be called only once");
        }
        instance.pictureTakingComponent = pictureTakingComponent;
    }

    public static PictureTaking getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Before calling PictureTaking.getInstance(), " +
                    "PictureTaking.setInstance() must be called");
        }
        return instance;
    }

    public PictureTakingComponent getComponent() {
        return pictureTakingComponent;
    }
}
