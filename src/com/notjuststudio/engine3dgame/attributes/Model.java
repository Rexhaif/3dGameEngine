package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.ModelData;
import com.notjuststudio.engine3dgame.ModelTexture;

/**
 * Created by George on 06.01.2017.
 */
public class Model extends Attribute{

    private ModelData data;
    private ModelTexture texture;

    public Model(ModelData data, ModelTexture texture) {
        super(Type.RENDER_MODEL);
        this.data = data;
        this.texture = texture;
    }

    public ModelData getData() {
        return data;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    @Override
    public Attribute getCopy() {
        return new Model(data, texture);
    }
}
