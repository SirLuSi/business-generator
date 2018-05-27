/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.business.code.generator.model;

import lombok.Data;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.io.Serializable;
import java.util.List;

/**
 * @author lusi
 */
@Data
public class TableClass implements Serializable {
    private static final long serialVersionUID = -746251813735169289L;

    private IntrospectedTable introspectedTable;

    private String tableName;
    private String variableName;
    private String lowerCaseName;
    private String shortClassName;
    private String fullClassName;
    private String packageName;
    private FullyQualifiedJavaType type;
    /**
     * 首字母小写
     */
    private String simpleName;
    /**
     * 首字母大写
     */
    private String simpleUpName;

    private List<ColumnField> pkFields;
    private List<ColumnField> baseFields;
    private List<ColumnField> blobFields;
    private List<ColumnField> allFields;

}
