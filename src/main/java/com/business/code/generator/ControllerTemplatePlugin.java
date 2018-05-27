package com.business.code.generator;

import com.business.code.generator.model.TableClass;

import java.util.Properties;

/**
 * @author lusi
 */
public class ControllerTemplatePlugin extends CommonTemplatePlugin {

    public static final String GENERATOR_CONTROLLER_FTL = "generator/controller.ftl";
    private static final String SUFFIX = "Controller";

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        setTemplatePath(GENERATOR_CONTROLLER_FTL);
        setFileName("${tableClass.simpleUpName}" + SUFFIX + ".java");
        setTargetPackage(properties.getProperty("targetPackage",
                properties.getProperty("targetControllerPackage")));
    }


    @Override
    public String getJavaReference(TableClass tableClass) {
        return (getTargetProject() + "/" + getTargetPackage() + "/"
                + tableClass.getSimpleUpName() + SUFFIX).replace(".", "/")+".java";
    }
}
