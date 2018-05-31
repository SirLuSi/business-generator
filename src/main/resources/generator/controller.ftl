package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ${targetServicePackage}.${tableClass.simpleUpName}Service;
import lombok.extern.slf4j.Slf4j;
/**
* 通用 Controller 代码生成器
* @description
* @author business.generator
*/
@Slf4j
@RestController
public class ${tableClass.simpleUpName}Controller {

    @Autowired
    private ${tableClass.simpleUpName}Service  ${tableClass.simpleName}Service;


}
