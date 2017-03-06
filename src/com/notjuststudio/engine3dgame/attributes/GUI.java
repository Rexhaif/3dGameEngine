package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.display.DisplayManager;
import com.notjuststudio.engine3dgame.display.Loader;
import com.notjuststudio.engine3dgame.data.TextureData;
import com.notjuststudio.engine3dgame.shader.ShaderProgram;
import com.notjuststudio.engine3dgame.util.MathUtil;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

/**
 * Created by Georgy on 05.03.2017.
 */
public class GUI extends Attribute {

    private static final List<GUI> guiList = new ArrayList<>();
    private static final GUIComparator comparator = new GUIComparator();

    private Vector2f offset;

    private int spriteID = 0;
    private ShaderProgram shader;

    private int width;
    private int height;

    public GUI(TextureData data, Vector2f offset, ShaderProgram shader) {
        super(Type.GUI);
        this.spriteID = Loader.loadTexture(data);
        this.shader = shader;

        this.width = data.getWidth();
        this.height = data.getHeight();

        this.offset = offset;

    }

    @Override
    public Attribute setEntity(Entity entity) {
        boolean changed = false;
        if (entity == null && this.entity != null)
            guiList.remove(this);
        if (entity != null && this.entity == null) {
            guiList.add(this);
            changed = true;
        }
        super.setEntity(entity);

        if (changed)
            guiList.sort(comparator);

        return this;
    }

    public static List<GUI> getGuiList() {
        return guiList;
    }

    public int getSpriteID() {
        return spriteID;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public Matrix3f getTransformationMatrix() {
        return createGuiTransformationMatrix();
    }

    public Matrix3f createGuiTransformationMatrix() {
        Vector2f off = Vector2f.sub(offset, new Vector2f(width / 2f, height / 2f), null);

        Vector3f position3f = entity.getWorldPosition();
        Vector2f position = new Vector2f(position3f.getX(), position3f.getY());

        Quaternion rotation = entity.getWorldRotation();
        double angleHalf = Math.acos(rotation.getW());
        float angle = (float)(Double.isNaN(angleHalf) ? Math.PI : angleHalf) * 2 * (rotation.getY() >= 0 ? 1 : -1);

        Vector3f scale3f = entity.getScale();
        Vector2f scale = new Vector2f(scale3f.getX(), scale3f.getY());

        Matrix3f result = new Matrix3f();
        result.setIdentity();

        Matrix3f.mul(result, MathUtil.createTranslationMatrix(Vector2f.sub(MathUtil.myDivision(MathUtil.scale(position, 2), new Vector2f(DisplayManager.getWidth(), DisplayManager.getHeight())), new Vector2f(1, 1), null)), result);


        Matrix3f.mul(result, MathUtil.createScaleMatrix(
                new Vector2f(
                        (DisplayManager.getHeight() / (float) DisplayManager.getWidth() * (width / (float) DisplayManager.getWidth())),
                        height / (float) DisplayManager.getWidth())
        ), result);


        Matrix3f.mul(result, MathUtil.createRotationMatrix(angle), result);
        Matrix3f.mul(result, MathUtil.createScaleMatrix(scale), result);

        Matrix3f.mul(result, MathUtil.createTranslationMatrix(MathUtil.myDivision(MathUtil.scale(off, -2), new Vector2f(width, height))), result);


        return result;
    }

    private static class GUIComparator implements Comparator<GUI> {
        @Override
        public int compare(GUI o1, GUI o2) {
            float firstZ = o1.entity.getWorldPosition().getZ();
            float secondZ = o2.entity.getWorldPosition().getZ();

            if (firstZ == secondZ)
                return 0;
            return firstZ < secondZ ? 1 : -1;
        }
    }
}
