package com.notjuststudio.engine3dgame.attributes;

import com.notjuststudio.engine3dgame.Game;
import com.notjuststudio.engine3dgame.attributes.scripts.PyScript;
import com.notjuststudio.engine3dgame.Entity;
import org.python.core.PyObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Georgy on 04.03.2017.
 */
public class Script extends Attribute {

    private static Set<PyScript> scripts = new HashSet<>();

    private PyScript pyScript;
    private String className;
    private Map<String, Object> args;

    public static Set<PyScript> getScripts() {
        return scripts;
    }

    public Script(String className, String pyCode) {
        this(className, pyCode, new HashMap<>());
    }

    public Script(String className, String pyCode, Map<String, Object> args) {
        super(Type.SCRIPT);
        this.className = className;
        this.args = args;

        Game.pythonInterpreter.exec(pyCode);
        PyObject object = Game.pythonInterpreter.get(className);
        pyScript = (PyScript)object.__call__().__tojava__(PyScript.class);
        Game.pythonInterpreter.set("__object__", pyScript);

        args.put("entity", null);
        for (Map.Entry<String, Object> entry : args.entrySet()) {
            Game.pythonInterpreter.set("__tmp__", entry.getValue());
            Game.pythonInterpreter.exec("__object__." + entry.getKey()  + "=__tmp__");
        }

        Game.pythonInterpreter.exec("del __tmp__");
        Game.pythonInterpreter.exec("del __object__");
        scripts.add(pyScript);
    }

    @Override
    public Attribute setEntity(Entity entity) {
        Game.pythonInterpreter.set("__object__", pyScript);
        Game.pythonInterpreter.set("__tmp__", entity);
        Game.pythonInterpreter.exec("__object__.entity=__tmp__");
        Game.pythonInterpreter.exec("del __tmp__");
        Game.pythonInterpreter.exec("del __object__");
        return super.setEntity(entity);
    }
}
