<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${Configuration.packageName}.${Configuration.path.dao}.${DaoClassName}">

    <resultMap id="${EntityName}ResultMap" type="${Configuration.packageName}.${Configuration.path.entity}.${ClassName}">
        ${ResultMap}
    </resultMap>

</mapper>