package com.business.code.generator;

import com.business.code.generator.model.TableClass;

import java.util.Properties;

/**
 * @author lusi
 */
public class ServiceTemplatePlugin extends CommonTemplatePlugin {

    public static final String GENERATOR_SERVICE_FTL = "generator/service.ftl";

    private static final  String  SUFFIX = "Service";

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        setTemplatePath(GENERATOR_SERVICE_FTL);
        setFileName("${tableClass.simpleUpName}"+SUFFIX+".java");
        setTargetPackage(properties.getProperty("targetPackage",
                properties.getProperty("targetServicePackage", this.getBasePackage() + ".servce")));
        super.properties.put("isImportBaseService", isImport((String) properties.get("baseService"),getTargetPackage()));

    }


    @Override
    public String getJavaReference(TableClass tableClass) {
        return (getTargetProject() + "/" + getTargetPackage() + "/"
                + tableClass.getSimpleUpName() + SUFFIX).replace(".", "/")+".java";
    }
}

