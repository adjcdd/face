package com.grgbanking.framework.manager.service;


import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.domains.manager.data_dictionary.json.DataDictionaryInitJson;
import com.grgbanking.framework.domains.manager.data_dictionary.json.DataDictionaryListJson;
import com.grgbanking.framework.domains.manager.data_dictionary.param.*;
import com.grgbanking.framework.domains.manager.data_dictionary.pojo.DataDictionaryPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.DataDictionaryMapper;
import com.grgbanking.framework.util.page.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjian17 on 2017/11/3.
 */
@Service("dataDictionaryService")
public class DataDictionaryService {

	private Logger logger = LoggerFactory.getLogger(DataDictionaryService.class);

	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	/**
	 * 查询数据字典
	 * @return
	 * @throws Exception
	 */
	public RestResponse getAllDataDictionary() throws Exception {
		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询数据字典!");
		RestResponse restResponse = new RestResponse();
		try {
			List<DataDictionaryListJson> dataDictionaryPojos = dataDictionaryMapper.getAllDataDictionary();
			Map<Byte,List<DataDictionaryInitJson>> dataDictionaryInitMap = new HashMap<>();
			for(DataDictionaryListJson dataDictionaryListJson:dataDictionaryPojos){
				Byte key = dataDictionaryListJson.getType();
				DataDictionaryInitJson dataDictionaryInitJson = new DataDictionaryInitJson();
				BeanUtils.copyProperties(dataDictionaryListJson,dataDictionaryInitJson);
				if(dataDictionaryInitMap.get(key)==null){
					List<DataDictionaryInitJson> dataDictionaryInitJsons= new ArrayList<DataDictionaryInitJson>();
					dataDictionaryInitJsons.add(dataDictionaryInitJson);
					dataDictionaryInitMap.put(key,dataDictionaryInitJsons);
				}else{
					dataDictionaryInitMap.get(key).add(dataDictionaryInitJson);
				}
			}
			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
			restResponse.getResponseHeader().setMessage("查询成功");
			restResponse.setResponseBody(dataDictionaryInitMap);
		} catch (Exception e) {
			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询查询数据字典出现异常", e);
			throw e;
		}
		return restResponse;
	}

	/**
	 * 查询数据字典列表
	 * @return
	 * @throws Exception
	 */
//	public RestResponse getDataDictionaryList(DataDictionaryListParam dataDictionaryListParam) throws Exception {
//		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询数据字典列表!");
//		RestResponse restResponse = new RestResponse();
//		try {
//			dataDictionaryListParam.setCurrentCount((dataDictionaryListParam.getPageNo() - 1) * dataDictionaryListParam.getPageSize());
//            //PageHelper.startPage(dataDictionaryListParam.getPageNo(), dataDictionaryListParam.getPageSize());
//			List<DataDictionaryPojo> dataDictionaryList = this.dataDictionaryMapper.getDataDictionaryList(dataDictionaryListParam);
//			Long totalCount = this.dataDictionaryMapper.getDataDictionaryListCount(dataDictionaryListParam);
//			PaginationJson page = new PaginationJson();
//			page.setPageNo(dataDictionaryListParam.getPageNo());
//			page.setPageSize(dataDictionaryListParam.getPageSize());
//			page.setData(dataDictionaryList);
//			page.setTotalCount(totalCount);
//			page.setTotalPage(PageUtil.calTotalPage(totalCount, dataDictionaryListParam.getPageSize()));
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//			restResponse.getResponseHeader().setMessage("查询成功");
//			restResponse.setResponseBody(page);
//		} catch (Exception e) {
//			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询查询数据字典出现异常", e);
//			throw e;
//		}
//		return restResponse;
//	}

	/**
	 * 新增数据字典
	 * @return
	 * @throws Exception
	 */
//	@Transactional(rollbackFor = Exception.class)
//	public RestResponse addDataDictionary(DataDictionaryAddParam dataDictionaryAddParam) throws Exception {
//		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增数据字典列表!");
//		RestResponse restResponse = new RestResponse();
//		try {
//			Integer flag = this.dataDictionaryMapper.checkIsExist(dataDictionaryAddParam);
//			if(flag!=null&&flag!=0) {
//				restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
//				restResponse.getResponseHeader().setMessage("数据字典已存在该数据");
//			}else {
//				DataDictionaryPojo dataDictionaryPojo = new DataDictionaryPojo();
//				BeanUtils.copyProperties(dataDictionaryAddParam,dataDictionaryPojo);
//				this.dataDictionaryMapper.addDataDictionary(dataDictionaryPojo);
//				restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//				restResponse.getResponseHeader().setMessage("新增成功");
//			}
//		} catch (Exception e) {
//			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增数据字典出现异常", e);
//			throw e;
//		}
//		return restResponse;
//	}
	/**
	 * 删除数据字典
	 * @return
	 * @throws Exception
	 */
//	@Transactional(rollbackFor = Exception.class)
//	public RestResponse deleteDataDictionary(DataDictionaryDeleteParam dataDictionaryDeleteParam) throws Exception {
//		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始删除数据字典列表!");
//		RestResponse restResponse = new RestResponse();
//		try {
//			this.dataDictionaryMapper.deleteDataDictionary(dataDictionaryDeleteParam);
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//			restResponse.getResponseHeader().setMessage("删除成功");
//		} catch (Exception e) {
//			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "删除数据字典出现异常", e);
//			throw e;
//		}
//		return restResponse;
//	}
	/**
	 * 编辑数据字典
	 * @return
	 * @throws Exception
	 */
//	@Transactional(rollbackFor = Exception.class)
//	 public RestResponse updateDataDictionary(DataDictionaryUpdateParam dataDictionaryUpdateParam) throws Exception {
//	 logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始编辑数据字典列表!");
//	 RestResponse restResponse = new RestResponse();
//	 try {
//	 this.dataDictionaryMapper.updateDataDictionary(dataDictionaryUpdateParam);
//	 restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//	 restResponse.getResponseHeader().setMessage("编辑成功");
//	 } catch (Exception e) {
//	 logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "编辑数据字典出现异常", e);
//	 throw e;
//	 }
//	 return restResponse;
//	 }
	/**
	 * 查询数据字典详情
	 * @return
	 * @throws Exception
	 */
//	public RestResponse getDataDictionaryInfo(DataDictionaryInfoParam dataDictionaryInfoParam) throws Exception {
//		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询数据字典详情!");
//		RestResponse restResponse = new RestResponse();
//		try {
//			DataDictionaryPojo dataDictionaryPojo = this.dataDictionaryMapper.getDataDictionaryInfo(dataDictionaryInfoParam);
//			restResponse.setResponseBody(dataDictionaryPojo);
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//			restResponse.getResponseHeader().setMessage("查询成功");
//		} catch (Exception e) {
//			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "编辑数据详情出现异常", e);
//			throw e;
//		}
//		return restResponse;
//	}
}
