package com.business.code.generator;

import com.business.code.generator.file.GenerateByListTemplateFile;
import com.business.code.generator.file.GenerateByTemplateFile;
import com.business.code.generator.formatter.ListTemplateFormatter;
import com.business.code.generator.formatter.TemplateFormatter;
import com.business.code.generator.model.TableClass;
import com.business.code.generator.model.TableColumnBuilder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author lusi
 */
@Data
public class CommonTemplatePlugin extends PluginAdapter {
    /**
     * 默认的模板格式化类
     */
    public static final String DEFAULT_TEMPLATEFORMATTER = "com.business.code.generator.formatter.FreemarkerTemplateFormatter";
    /**
     * 单个文件模式
     */
    private String singleMode;
    /**
     * 项目路径（目录需要已经存在）
     */
    private String targetProject;

    /**
     * 生成的包（路径不存在则创建）
     */
    private String targetPackage;
    /**
     * 模板路径
     */
    private String templatePath;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 文件名模板，通过模板方式生成文件名，包含后缀
     */
    private String fileName;
    /**
     * 模板生成器
     */
    private Object templateFormatter;
    private String templateFormatterClass;
    private Set<TableClass> cacheTables;

    /**
     * 编码
     */
    private String encoding;

    /**
     *
     */
    private String basePackage;

    private boolean isOverride = false;

    /**
     * 列转换为字段
     *
     * @param introspectedColumn
     * @return
     */
    public static Field convertToJavaBeansField(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
        String property = introspectedColumn.getJavaProperty();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fqjt);
        field.setName(property);
        return field;
    }

    /**
     * 读取文件
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected String read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
        StringBuilder stringBuffer = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            stringBuffer.append(line).append("\n");
            line = reader.readLine();
        }
        return stringBuffer.toString();
    }

    public boolean validate(List<String> warnings) {
        boolean right = true;
        if (!StringUtility.stringHasValue(fileName)) {
            warnings.add("没有配置 \"fileName\" 文件名模板，因此不会生成任何额外代码!");
            right = false;
        }
        if (!StringUtility.stringHasValue(templatePath)) {
            warnings.add("没有配置 \"templatePath\" 模板路径，因此不会生成任何额外代码!");
            right = false;
        } else {
            try {
                URL resourceUrl = null;
                try {
                    resourceUrl = ObjectFactory.getResource(templatePath);
                } catch (Exception e) {
                    warnings.add("本地加载\"templatePath\" 模板路径失败，尝试 URL 方式获取!");
                }
                if (resourceUrl == null) {
                    resourceUrl = new URL(templatePath);
                }
                InputStream inputStream = resourceUrl.openConnection().getInputStream();
                templateContent = read(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                warnings.add("读取模板文件出错: " + e.getMessage());
                right = false;
            }
        }
        if (!StringUtility.stringHasValue(templateFormatterClass)) {
            templateFormatterClass = DEFAULT_TEMPLATEFORMATTER;
            warnings.add("没有配置 \"templateFormatterClass\" 模板处理器，使用默认的处理器!");
        }
        try {
            templateFormatter = Class.forName(templateFormatterClass).newInstance();
        } catch (Exception e) {
            warnings.add("初始化 templateFormatter 出错:" + e.getMessage());
            right = false;
        }
        if (!right) {
            return false;
        }
        int errorCount = 0;
        if (!StringUtility.stringHasValue(targetProject)) {
            errorCount++;
            warnings.add("没有配置 \"targetProject\" 路径!");
        }
        if (!StringUtility.stringHasValue(targetPackage)) {
            errorCount++;
            warnings.add("没有配置 \"targetPackage\" 路径!");
        }
        if (errorCount >= 2) {
            warnings.add("由于没有配置任何有效路径，不会生成任何额外代码!");
            return false;
        }
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        TableClass tableClass = TableColumnBuilder.build(introspectedTable);
        if ("TRUE".equalsIgnoreCase(singleMode)) {
            if (isReWriteFile(tableClass)) {
                list.add(new GenerateByTemplateFile(tableClass, (TemplateFormatter) templateFormatter, properties, targetProject, targetPackage, fileName, templateContent));
            }
        } else {
            cacheTables.add(tableClass);
        }
        return list;
    }

    public String getJavaReference(TableClass tableClass) {
        return null;
    }

    public boolean isReWriteFile(TableClass tableClass) {
        if (isOverride) {
            return true;
        }
        String ref = getJavaReference(tableClass);
        System.out.println(ref);
        File file = new File(ref);
        if(!file.exists()){
            System.out.println("文件不存在!");
            return true;
        }
        return false;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
        if (CollectionUtils.isNotEmpty(cacheTables)) {
            list.add(new GenerateByListTemplateFile(cacheTables, (ListTemplateFormatter) templateFormatter, properties, targetProject, targetPackage, fileName, templateContent));
        }
        return list;
    }

    @Override
    public void setProperties(Properties properties) {
        if (properties == null) {
            properties = new Properties();
        }
        copyPeoperties(this.getContext().getProperties(), properties);
        this.singleMode = properties.getProperty("singleMode", "true");
        if (!"TRUE".equalsIgnoreCase(singleMode)) {
            this.cacheTables = new LinkedHashSet<TableClass>();
        }
        this.targetProject = properties.getProperty("targetProject", properties.getProperty("targetJavaProject"));
        this.targetPackage = properties.getProperty("targetPackage");
        this.templatePath = properties.getProperty("templatePath");
        this.fileName = properties.getProperty("fileName");
        this.templateFormatterClass = properties.getProperty("templateFormatter");
        this.encoding = properties.getProperty("encoding", "UTF-8");
        this.basePackage = properties.getProperty("basePackage");
        this.isOverride = Boolean.parseBoolean(properties.getProperty("isOverride", "false"));
        setDefaultProperties(basePackage, properties);
        super.setProperties(properties);
    }


    private void copyPeoperties(Properties sourceProperties, Properties targetProperties) {
        for (Object key : sourceProperties.keySet()) {
            targetProperties.put( key, sourceProperties.getProperty((String) key));
        }

    }

    private void setDefaultProperties(String basePackage, Properties properties) {
        String targetControllerPackage = properties.getProperty("targetControllerPackage");
        String targetServicePackage = properties.getProperty("targetServicePackage");
        String targetServiceImplPackage = properties.getProperty("targetServiceImplPackage");

        if (targetControllerPackage == null) {
            properties.put("targetControllerPackage", basePackage + ".controller");
        }
        if (targetServicePackage == null) {
            properties.put("targetServicePackage", basePackage + ".service");
        }
        if (targetServiceImplPackage == null) {
            properties.put("targetServiceImplPackage", basePackage + ".service.impl");
        }
    }

    protected boolean isImport(String baseServiceImpl, String targetPackage) {

        System.out.println(baseServiceImpl);
        System.out.println(targetPackage);
        String s = StringUtils.trimToEmpty(baseServiceImpl);
        if(s.substring(0,s.lastIndexOf(".")).equals(targetPackage)){
            return false;
        }
        return true;
    }


}
