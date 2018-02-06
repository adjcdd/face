package com.grgbanking.framework.manager.mapper;

import java.util.List;

/**
 * Created by wyf on 2017/7/19.
 */
public interface BaseMapper {

	List<?> getAllExport(Object object);

	List<?> getExportByIds(List<Integer> checkedIdList);

}
