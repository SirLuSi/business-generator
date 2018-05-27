package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ${targetServicePackage}.${tableClass.simpleUpName}Service;

/**
* 通用 Controller 代码生成器
*
* @author business.generator
*/
@RestController
public class ${tableClass.simpleUpName}Controller {

    @Autowired
    private ${tableClass.simpleUpName}Service  ${tableClass.simpleName}Service;


}
