package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Keeper;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;

import java.util.*;

/**
 * Created by George on 06.01.2017.
 */
public class Model extends Attribute{

    private ModelData data;
    private ModelTexture texture;

    private static Map<Model, List<Keeper>> models = new HashMap<>();

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

    public void addToMap(Keeper keeper) {
        if (models.keySet().contains(this)){
            models.get(this).add(keeper);
        } else {
            List<Keeper> keepers = new ArrayList<>();
            keepers.add(keeper);
            models.put(this, keepers);
        }
    }

    public static Set<Model> getKeySet() {
        return models.keySet();
    }

    public static List<Keeper> getList(Model model) {
        return models.get(model);
    }

    @Override
    public Attribute getCopy() {
        return new Model(data, texture);
    }
}
