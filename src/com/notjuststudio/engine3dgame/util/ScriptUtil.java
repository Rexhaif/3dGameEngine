package com.notjuststudio.engine3dgame.util;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Created by Georgy on 04.03.2017.
 */
public class ScriptUtil {

    public static PyObject getPyObject(PythonInterpreter pythonInterpreter, Object object) {
        pythonInterpreter.set("__tmp__", object);
        PyObject result = pythonInterpreter.get("__tmp__");
        pythonInterpreter.exec("del __tmp__");
        return result;
    }
}
