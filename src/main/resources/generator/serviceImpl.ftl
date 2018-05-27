package ${package};

<#if baseServiceImpl??>
import ${tableClass.fullClassName};
import ${baseServiceImpl};
</#if>
import ${targetServicePackage}.${tableClass.simpleUpName}Service;
import org.springframework.stereotype.Service;
/**
* 通用 serviceImpl 代码生成器
*
* @author business.generator
*/
@Service
public class ${tableClass.simpleUpName}ServiceImpl <#if baseServiceImpl??>extends BaseServiceImpl<${tableClass.shortClassName}></#if> implements ${tableClass.simpleUpName}Service{

}




