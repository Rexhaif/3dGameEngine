package com.notjuststudio.engine3dgame.colladaConverter;

import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.attributes.model.ModelData;
import com.notjuststudio.engine3dgame.colladaConverter.colladaschema.*;
import com.notjuststudio.engine3dgame.osfConverter.VAOContainer;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class COLLADAFileLoader {

	public static ModelData loadDAE(String daeFileName) {
		ArraysContainer container = loadDAEtoVaoContainer(daeFileName);
		return Loader.createModelData(container.createVAOContainer());
	}

	public static VAOContainer loadDAEtoVAOContainer(String daeFileName) {
		ArraysContainer container = loadDAEtoVaoContainer(daeFileName);
		return container.createVAOContainer();
	}

	private static ArraysContainer loadDAEtoVaoContainer(String daeFileName) {

		COLLADA collada = null;

		try {
			JAXBContext context = JAXBContext.newInstance(COLLADA.class);
			Unmarshaller un = context.createUnmarshaller();
			collada = (COLLADA) un.unmarshal(new File(daeFileName));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		Mesh mesh = null;
		String geometryID = "";

		for (Object library : collada.getLibraryAnimationsAndLibraryAnimationClipsAndLibraryCameras()) {
			if (library instanceof LibraryGeometries) {
				geometryID = ((LibraryGeometries) library).getGeometries().get(0).getId();
				mesh = ((LibraryGeometries) library).getGeometries().get(0).getMesh();
				break;
			}
		}

		List<Vertex> vertices = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<String> bones = new ArrayList<>();
		List<Matrix4f> bindPoses = new ArrayList<>();
		List<Float> weights = new ArrayList<>();
		Map<Integer, List<Bone>> bonesForVertices = new HashMap<>();
		List<Integer> indices = new ArrayList<>();

		Source source = null;
		Double[] doubleFloats;

		for (Source thisSource : mesh.getSources()) {
			if (thisSource.getId().equals(geometryID + "-positions")) {
				source = thisSource;
				break;
			}
		}

		if (source == null)
			try {
				throw new IOException("Illegal DAE file");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

		doubleFloats = new Double[source.getFloatArray().getValues().size()];
		doubleFloats = source.getFloatArray().getValues().toArray(doubleFloats);

		for (int i = 0; i < source.getTechniqueCommon().getAccessor().getCount().intValue(); i++) {
			vertices.add(new Vertex(vertices.size(), new Vector3f(
					doubleFloats[i * 3].floatValue(),
					doubleFloats[i * 3 + 1].floatValue(),
					doubleFloats[i * 3 + 2].floatValue()
			)));
		}


		source = null;

		for (Source thisSource : mesh.getSources()) {
			if (thisSource.getId().equals(geometryID + "-normals")) {
				source = thisSource;
				break;
			}
		}

		if (source == null)
			try {
				throw new IOException("Illegal DAE file");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

		doubleFloats = new Double[source.getFloatArray().getValues().size()];
		doubleFloats = source.getFloatArray().getValues().toArray(doubleFloats);

		for (int i = 0; i < source.getTechniqueCommon().getAccessor().getCount().intValue(); i++) {
			normals.add(new Vector3f(
					doubleFloats[i * 3].floatValue(),
					doubleFloats[i * 3 + 1].floatValue(),
					doubleFloats[i * 3 + 2].floatValue()
			));
		}


		source = null;

		for (Source thisSource : mesh.getSources()) {
			if (thisSource.getId().equals(geometryID + "-map-0")) {
				source = thisSource;
				break;
			}
		}

		if (source == null)
			try {
				throw new IOException("Illegal DAE file");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}

		doubleFloats = new Double[source.getFloatArray().getValues().size()];
		doubleFloats = source.getFloatArray().getValues().toArray(doubleFloats);

		for (int i = 0; i < source.getTechniqueCommon().getAccessor().getCount().intValue(); i++) {
			textures.add(new Vector2f(
					doubleFloats[i * 2].floatValue(),
					doubleFloats[i * 2 + 1].floatValue()
			));
		}


		int[] bigIntegers = new int[0];
		int count = 0;
		List<BigInteger> stride;
		for (Object simple : mesh.getLinesAndLinestripsAndPolygons()) {
			if (simple instanceof Polylist) {
				bigIntegers = new int[((Polylist) simple).getP().size()];
				for (int i = 0; i < bigIntegers.length; i++) {
					bigIntegers[i] = Integer.parseInt((String) ((Object) ((Polylist) simple).getP().get(i)));
				}
				count = ((Polylist) simple).getCount().intValue();
				stride = ((Polylist) simple).getVcount();
				break;
			}
		}

		for (int i = 0; i < count * 3; i += 3) {
			int[] vertex = {
					bigIntegers[i * 3],
					bigIntegers[i * 3 + 1],
					bigIntegers[i * 3 + 2],
			};
			processVertex(vertex, vertices, indices);
		}

		removeUnusedVertices(vertices);

		Skin skin = null;
		String controllerID = "";

		for (Object library : collada.getLibraryAnimationsAndLibraryAnimationClipsAndLibraryCameras()) {
			if (library.getClass() == LibraryControllers.class) {
				if (((LibraryControllers) library).getControllers().isEmpty())
					break;
				controllerID = ((LibraryControllers) library).getControllers().get(0).getId();
				skin = ((LibraryControllers) library).getControllers().get(0).getSkin();
				break;
			}
		}

		if (skin != null) {

			source = null;

			for (Source thisSource : skin.getSources()) {
				if (thisSource.getId().equals(geometryID + "-joints")) {
					source = thisSource;
					break;
				}
			}

			if (source == null)
				try {
					throw new IOException("Illegal DAE file");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}

			for (int i = 0; i < source.getTechniqueCommon().getAccessor().getCount().intValue(); i++) {
				bones.add(source.getNameArray().getValues().get(i));
			}

			source = null;

			for (Source thisSource : skin.getSources()) {
				if (thisSource.getId().equals(geometryID + "-bind_poses")) {
					source = thisSource;
					break;
				}
			}

			if (source == null)
				try {
					throw new IOException("Illegal DAE file");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}

			for (int i = 0; i < source.getFloatArray().getCount().intValue(); i+=16) {
				Matrix4f result = new Matrix4f();
				result.m00 = source.getFloatArray().getValues().get(i).floatValue();
				result.m01 = source.getFloatArray().getValues().get(i + 1).floatValue();
				result.m02 = source.getFloatArray().getValues().get(i + 2).floatValue();
				result.m03 = source.getFloatArray().getValues().get(i + 3).floatValue();
				result.m10 = source.getFloatArray().getValues().get(i + 4).floatValue();
				result.m11 = source.getFloatArray().getValues().get(i + 5).floatValue();
				result.m12 = source.getFloatArray().getValues().get(i + 6).floatValue();
				result.m13 = source.getFloatArray().getValues().get(i + 7).floatValue();
				result.m20 = source.getFloatArray().getValues().get(i + 8).floatValue();
				result.m21 = source.getFloatArray().getValues().get(i + 9).floatValue();
				result.m22 = source.getFloatArray().getValues().get(i + 10).floatValue();
				result.m23 = source.getFloatArray().getValues().get(i + 11).floatValue();
				result.m30 = source.getFloatArray().getValues().get(i + 12).floatValue();
				result.m31 = source.getFloatArray().getValues().get(i + 13).floatValue();
				result.m32 = source.getFloatArray().getValues().get(i + 14).floatValue();
				result.m33 = source.getFloatArray().getValues().get(i + 15).floatValue();
				bindPoses.add(result);
			}

			source = null;

			for (Source thisSource : skin.getSources()) {
				if (thisSource.getId().equals(geometryID + "-weights")) {
					source = thisSource;
					break;
				}
			}

			if (source == null)
				try {
					throw new IOException("Illegal DAE file");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}

			for (int i = 0; i < source.getFloatArray().getCount().intValue(); i++) {
				weights.add(source.getFloatArray().getValues().get(i).floatValue());
			}

			int iterator = 0;
			for (int i = 0; i < skin.getVertexWeights().getCount().intValue(); i++) {
				List<Bone> attached = new ArrayList<>();
				for (int j = 0; j < skin.getVertexWeights().getVcount().get(i).intValue(); j++) {
					attached.add(new Bone(
							skin.getVertexWeights().getV().get(iterator++).intValue(),
							skin.getVertexWeights().getV().get(iterator++).intValue()));
				}
				bonesForVertices.put(i, attached);
			}
		}

		float[] verticesArray = new float[vertices.size() * 3];
		float[] texturesArray = new float[vertices.size() * 2];
		float[] normalsArray = new float[vertices.size() * 3];

		if (skin != null) {

		} else {
			convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray, normalsArray);
		}

		int[] indicesArray = convertIndicesListToArray(indices);

		return new ArraysContainer(indicesArray, verticesArray, texturesArray, normalsArray);
	}

	private static class ArraysContainer {
		int[] indicesArray = new int[0];
		float[] verticesArray = new float[0];
		float[] texturesArray = new float[0];
		float[] normalsArray = new float[0];
		int[] boneIndicesArray = new int[0];
		float[] boneWeightsArray = new float[0];

		ArraysContainer(int[] indicesArray, float[] verticesArray, float[] texturesArray, float[] normalsArray) {
			this.indicesArray = indicesArray;
			this.verticesArray = verticesArray;
			this.texturesArray = texturesArray;
			this.normalsArray = normalsArray;
		}

		public int[] getIndicesArray() {
			return indicesArray;
		}

		public float[] getVerticesArray() {
			return verticesArray;
		}

		public float[] getTexturesArray() {
			return texturesArray;
		}

		public float[] getNormalsArray() {
			return normalsArray;
		}

		public int[] getBoneIndicesArray() {
			return boneIndicesArray;
		}

		public float[] getBoneWeightsArray() {
			return boneWeightsArray;
		}

		public ArraysContainer setIndicesArray(int[] indicesArray) {
			this.indicesArray = indicesArray;
			return this;
		}

		public ArraysContainer setVerticesArray(float[] verticesArray) {
			this.verticesArray = verticesArray;
			return this;
		}

		public ArraysContainer setTexturesArray(float[] texturesArray) {
			this.texturesArray = texturesArray;
			return this;
		}

		public ArraysContainer setNormalsArray(float[] normalsArray) {
			this.normalsArray = normalsArray;
			return this;
		}

		public ArraysContainer setBoneIndicesArray(int[] boneIndicesArray) {
			this.boneIndicesArray = boneIndicesArray;
			return this;
		}

		public ArraysContainer setBoneWeightsArray(float[] boneWeightsArray) {
			this.boneWeightsArray = boneWeightsArray;
			return this;
		}

		public VAOContainer createVAOContainer() {
			VAOContainer result = new VAOContainer();
			if (indicesArray.length > 0)
				result.setIndices(Loader.storeDataInIntBufferBuffer(indicesArray));
			if (verticesArray.length > 0)
				result.setPositions(Loader.storeDataInFloatBuffer(verticesArray));
			if (texturesArray.length > 0)
				result.setUvCoords(Loader.storeDataInFloatBuffer(texturesArray));
			if (normalsArray.length > 0)
				result.setNormals(Loader.storeDataInFloatBuffer(normalsArray));
			if (boneIndicesArray.length > 0)
				result.setBoneIndices(Loader.storeDataInIntBufferBuffer(boneIndicesArray));
			if (boneWeightsArray.length > 0)
				result.setBoneWeights(Loader.storeDataInFloatBuffer(boneWeightsArray));
			return result;
		}
	}

	private static void processVertex(int[] vertex, List<Vertex> vertices, List<Integer> indices) {
		int index = vertex[0];
		Vertex currentVertex = vertices.get(index);
		int textureIndex = vertex[2];
		int normalIndex = vertex[1];
		if (!currentVertex.isSet()) {
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
		} else {
			dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
					vertices);
		}
	}

	private static int[] convertIndicesListToArray(List<Integer> indices) {
		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indicesArray.length; i++) {
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	private static void convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures,
											List<Vector3f> normals, float[] verticesArray, float[] texturesArray,
											float[] normalsArray) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex currentVertex = vertices.get(i);
			Vector3f position = currentVertex.getPosition();
			Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
			Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
			verticesArray[i * 3] = position.x;
			verticesArray[i * 3 + 1] = position.y;
			verticesArray[i * 3 + 2] = position.z;
			texturesArray[i * 2] = textureCoord.x;
			texturesArray[i * 2 + 1] = 1 - textureCoord.y;
			normalsArray[i * 3] = normalVector.x;
			normalsArray[i * 3 + 1] = normalVector.y;
			normalsArray[i * 3 + 2] = normalVector.z;
		}
	}

	private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
													   int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
						indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
			}

		}
	}

	private static void removeUnusedVertices(List<Vertex> vertices) {
		for (Vertex vertex : vertices) {
			if (!vertex.isSet()) {
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

}