package com.business.code.generator;

import com.business.code.generator.model.TableClass;

import java.util.Properties;

/**
 * @author lusi
 */
public class ServiceImplTemplatePlugin extends CommonTemplatePlugin {


    public static final String GENERATOR_SERVICE_FTL = "generator/serviceImpl.ftl";

    private static final String SUFFIX = "ServiceImpl";

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        setTemplatePath(GENERATOR_SERVICE_FTL);
        setFileName("${tableClass.simpleUpName}" + SUFFIX + ".java");
        setTargetPackage(properties.getProperty("targetPackage"
                , properties.getProperty("targetServiceImplPackage", this.getBasePackage() + ".service.impl")));
    }

    @Override
    public String getJavaReference(TableClass tableClass) {
        return (getTargetProject() + "/" + getTargetPackage() + "/"
                + tableClass.getSimpleUpName() + SUFFIX).replace(".", "/")+".java";
    }

}
