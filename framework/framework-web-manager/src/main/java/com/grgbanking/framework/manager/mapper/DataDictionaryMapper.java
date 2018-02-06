package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.manager.data_dictionary.json.DataDictionaryListJson;
import com.grgbanking.framework.domains.manager.data_dictionary.param.*;
import com.grgbanking.framework.domains.manager.data_dictionary.pojo.DataDictionaryPojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wjian17 on 2017/11/3.
 */
@Service("dataDictionaryMapper")
public interface DataDictionaryMapper extends BaseMapper {

	DataDictionaryPojo getDataDictionaryInfo(DataDictionaryInfoParam dataDictionaryInfoParam);

	List<DataDictionaryListJson> getAllDataDictionary();

	List<DataDictionaryPojo> getDataDictionaryList(DataDictionaryListParam dataDictionaryListParam);

	Long getDataDictionaryListCount(DataDictionaryListParam dataDictionaryListParam);
	/*

	void addDataDictionary(DataDictionaryPojo dataDictionaryPojo);

	void updateDataDictionary(DataDictionaryUpdateParam dataDictionaryUpdateParam);

	void deleteDataDictionary(DataDictionaryDeleteParam dataDictionaryDeleteParam);

    Integer checkIsExist(DataDictionaryAddParam dataDictionaryAddParam);*/
}
