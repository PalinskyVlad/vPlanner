package by.gomel.iba.vPlanner.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class BaseMapper {
    final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
}
