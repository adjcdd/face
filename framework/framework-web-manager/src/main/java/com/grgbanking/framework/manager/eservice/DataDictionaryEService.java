package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.data_dictionary.json.DataDictionaryInitJson;
import com.grgbanking.framework.domains.manager.data_dictionary.json.DataDictionaryListJson;
import com.grgbanking.framework.domains.manager.data_dictionary.param.DataDictionaryInfoParam;
import com.grgbanking.framework.domains.manager.data_dictionary.param.DataDictionaryListParam;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.DataDictionaryService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjian17 on 2017/11/3.
 */
public class DataDictionaryEService {


//	@EService("ES-DataDictionaryPojo-All-T")
//	@ManagerOperate("查询数据字典")
//	public RestResponse getAllDataDictionary() throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
////			restResponse = this.dataDictionaryService.getAllDataDictionary();
//			Map<Byte,List<DataDictionaryInitJson>> dataDictionaryInitMap = new HashMap<>();
//			List<DataDictionaryInitJson> dataDictionaryPojos = new ArrayList();
//			DataDictionaryInitJson dataDictionaryListJson1 = new DataDictionaryInitJson();
//			dataDictionaryListJson1.setCode("1");
//			dataDictionaryListJson1.setName("启用");
//			dataDictionaryPojos.add(dataDictionaryListJson1);
//			DataDictionaryInitJson dataDictionaryListJson2 = new DataDictionaryInitJson();
//			dataDictionaryListJson2.setCode("0");
//			dataDictionaryListJson2.setName("禁用");
//			dataDictionaryPojos.add(dataDictionaryListJson2);
//			dataDictionaryInitMap.put((byte)13, dataDictionaryPojos);
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
//			restResponse.getResponseHeader().setMessage("查询成功");
//			restResponse.setResponseBody(dataDictionaryInitMap);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("查询数据字典出现异常");
//		}
//		return restResponse;
//	}

	private DataDictionaryService dataDictionaryService = BeanFactoryConfig.getBean("dataDictionaryService");

	@EService("ES-DataDictionaryPojo-All-T")
	@ManagerOperate("查询数据字典")
	public RestResponse getAllDataDictionary() throws Exception{
		RestResponse restResponse = new RestResponse();
		try {
			restResponse = this.dataDictionaryService.getAllDataDictionary();
		}catch (Exception e){
		restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
		restResponse.getResponseHeader().setMessage("查询数据字典出现异常");
		}
		return restResponse;
		}
//	@EService("ES-DataDictionaryPojo-List-T")
//	@ManagerOperate("数据字典列表")
//	public RestResponse getDataDictionaryList(DataDictionaryListParam dictionaryListParam) throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
//			restResponse = this.dataDictionaryService.getDataDictionaryList(dictionaryListParam);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("查询数据字典列表出现异常");
//		}
//		return restResponse;
//	}

//	@EService("ES-DataDictionaryPojo-Add-T")
//	@ManagerOperate("新增数据字典")
//	public RestResponse addDataDictionary(DataDictionaryAddParam dataDictionaryAddParam) throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
//			restResponse = this.dataDictionaryService.addDataDictionary(dataDictionaryAddParam);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("新增数据字典出现异常");
//		}
//		return restResponse;
//	}

//	@EService("ES-DataDictionaryPojo-Delete-T")
//	@ManagerOperate("删除数据字典")
//	public RestResponse deleteDataDictionary(DataDictionaryDeleteParam dataDictionaryDeleteParam) throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
//			restResponse = this.dataDictionaryService.deleteDataDictionary(dataDictionaryDeleteParam);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("删除数据字典出现异常");
//		}
//		return restResponse;
//	}

//	@EService("ES-DataDictionaryPojo-Edit-T")
//	@ManagerOperate("编辑数据字典")
//	public RestResponse editDataDictionary(DataDictionaryUpdateParam dataDictionaryUpdateParam) throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
//			restResponse = this.dataDictionaryService.updateDataDictionary(dataDictionaryUpdateParam);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("编辑数据字典出现异常");
//		}
//		return restResponse;
//	}
//	@EService("ES-DataDictionaryPojo-Info-T")
//	@ManagerOperate("查询数据字典详情")
//	public RestResponse getDataDictionaryInfo(DataDictionaryInfoParam dataDictionaryInfoParam) throws Exception{
//		RestResponse restResponse = new RestResponse();
//		try {
//			restResponse = this.dataDictionaryService.getDataDictionaryInfo(dataDictionaryInfoParam);
//		}catch (Exception e){
//			restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//			restResponse.getResponseHeader().setMessage("查询数据字典详情出现异常");
//		}
//		return restResponse;
//	}
}
