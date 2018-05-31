package ${package};

<#if baseService??>
import ${tableClass.fullClassName};
import ${baseService};
</#if>
/**
* 通用 service 代码生成器
* @description
* @author business.generator
*/
public interface ${tableClass.simpleUpName}Service <#if baseService??>extends BaseService<${tableClass.shortClassName}></#if> {

}




