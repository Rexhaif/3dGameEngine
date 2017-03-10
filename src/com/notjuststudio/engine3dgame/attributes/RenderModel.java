package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.display.Loader;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.attributes.model.ModelTexture;

import java.util.*;

/**
 * Created by George on 06.01.2017.
 */
public class RenderModel extends Attribute{

    
    private ModelData data;
    private ModelTexture texture;

    private static Map<RenderModel, List<Entity>> models = new HashMap<>();
    private static Map<ModelData, List<RenderModel>> modelDatas = new HashMap<>();

    public RenderModel(ModelData data, ModelTexture texture) {
        super(Type.RENDER_MODEL);
        List<RenderModel> renderModelList = modelDatas.get(data);
        if (renderModelList != null) {
            renderModelList.add(this);
        } else {
            renderModelList = new ArrayList<>();
            renderModelList.add(this);
            modelDatas.put(data, renderModelList);
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

    public static Map<RenderModel, List<Entity>> getModelsMap() {
        return models;
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
        modelDatas.remove(data).forEach(RenderModel::delete);
    }

    @Override
    public Attribute setEntity(Entity entity) {
        super.setEntity(entity);
        addToMap(entity);
        return this;
    }
}
