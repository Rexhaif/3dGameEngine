package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Entity;
import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;

import java.util.*;

/**
 * Created by George on 06.01.2017.
 */
public class Model extends Attribute{

    private ModelData data;
    private ModelTexture texture;

    private static Map<Model, List<Entity>> models = new HashMap<>();
    private static Map<ModelData, List<Model>> modelDatas = new HashMap<>();

    public Model(ModelData data, ModelTexture texture) {
        super(Type.RENDER_MODEL);
        List<Model> modelList = modelDatas.get(data);
        if (modelList != null) {
            modelList.add(this);
        } else {
            modelList = new ArrayList<>();
            modelList.add(this);
            modelDatas.put(data, modelList);
        }
        this.data = data;
        this.texture = texture;
    }

    public ModelData getData() {
        return data;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    private void addToMap(Entity entity) {
        List<Entity> entityList = models.get(this);
        if (entityList != null){
            entityList.add(entity);
        } else {
            entityList = new ArrayList<>();
            entityList.add(entity);
            models.put(this, entityList);
        }
    }

    public static Set<Model> getKeySet() {
        return models.keySet();
    }

    public static List<Entity> getList(Model model) {
        return models.get(model);
    }

    @Override
    public void delete() {
        super.delete();
        Loader.deleteTexture(texture.getTextureID());
        texture.getShaderProgram().delete();
        for (Entity entity : models.remove(this)) {
            entity.getAttributes().remove(entity.getAttributeOfType(Type.RENDER_MODEL));
        }
    }

    private static void deleteModelData(ModelData data) {
        Loader.deleteVAO(data.getVaoID());
        modelDatas.remove(data).forEach(Model::delete);
    }

    @Override
    public Attribute setEntity(Entity entity) {
        super.setEntity(entity);
        addToMap(entity);
        return this;
    }
}
