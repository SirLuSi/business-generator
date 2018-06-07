package ${package};

<#if baseServiceImpl??>
import ${tableClass.fullClassName};
<#if isImportBaseServiceImpl>
import ${baseServiceImpl};
</#if>
</#if>
import ${targetServicePackage}.${tableClass.simpleUpName}Service;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
* 通用 serviceImpl 代码生成器
* @description
* @author business.generator
*/
@Slf4j
@Service
public class ${tableClass.simpleUpName}ServiceImpl <#if baseServiceImpl??>extends BaseServiceImpl<${tableClass.shortClassName}></#if> implements ${tableClass.simpleUpName}Service{

}




