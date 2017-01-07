package com.notjuststudio.engine3dgame.colladaConverter;

import com.notjuststudio.engine3dgame.Loader;
import com.notjuststudio.engine3dgame.ModelData;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class COLLADAFileLoader {

	public static ModelData loadDAE(String daeFileName) {

        COLLADA collada = null;

        try {
			JAXBContext context = JAXBContext.newInstance(COLLADA.class);
			Unmarshaller un = context.createUnmarshaller();
            collada = (COLLADA) un.unmarshal(new File(daeFileName));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

        COLLADA.LibraryGeometries.Geometry.Mesh mesh = collada.getLibraryGeometries().geometry.getMesh();

		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();

        String[] stringFloats = mesh.getSource().get(0).floatArray.getValue().split(" ");

        for (int i = 0; i < mesh.getSource().get(0).getTechniqueCommon().getAccessor().getCount(); i++) {
            vertices.add(new Vertex(vertices.size(), new Vector3f(
                    Float.parseFloat(stringFloats[i * 3]),
                    Float.parseFloat(stringFloats[i * 3 + 1]),
                    Float.parseFloat(stringFloats[i * 3 + 2])
                    )));
        }

        stringFloats = mesh.getSource().get(1).floatArray.getValue().split(" ");

        for (int i = 0; i < mesh.getSource().get(1).getTechniqueCommon().getAccessor().getCount(); i++) {
            normals.add(new Vector3f(
                    Float.parseFloat(stringFloats[i * 3]),
                    Float.parseFloat(stringFloats[i * 3 + 1]),
                    Float.parseFloat(stringFloats[i * 3 + 2])
            ));
        }

		stringFloats = mesh.getSource().get(2).floatArray.getValue().split(" ");

        for (int i = 0; i < mesh.getSource().get(2).getTechniqueCommon().getAccessor().getCount(); i++) {
            textures.add(new Vector2f(
                    Float.parseFloat(stringFloats[i * 2]),
                    Float.parseFloat(stringFloats[i * 2 + 1])
            ));
        }

		stringFloats = mesh.getPolylist().getP().split(" ");

        for (int i = 0; i < mesh.getPolylist().getCount() * 3; i++) {
            int[] vertex = {
                    Integer.parseInt(stringFloats[i * 3]),
                    Integer.parseInt(stringFloats[i * 3 + 1]),
                    Integer.parseInt(stringFloats[i * 3 + 2]),
            };
            processVertex(vertex, vertices, indices);
        }

		removeUnusedVertices(vertices);

		float[] verticesArray = new float[vertices.size() * 3];
		float[] texturesArray = new float[vertices.size() * 2];
		float[] normalsArray = new float[vertices.size() * 3];

		convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray, normalsArray);

		int[] indicesArray = convertIndicesListToArray(indices);

		return Loader.createModelData(indicesArray, verticesArray, texturesArray, normalsArray);
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
	
	private static void removeUnusedVertices(List<Vertex> vertices){
		for(Vertex vertex:vertices){
			if(!vertex.isSet()){
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

}