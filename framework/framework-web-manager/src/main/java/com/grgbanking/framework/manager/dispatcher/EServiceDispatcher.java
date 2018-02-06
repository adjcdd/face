package com.grgbanking.framework.manager.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.cache.EsServiceCache;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import com.grgbanking.framework.util.json.JsonFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wyf on 2017/8/7.
 * 系统服务的分发器
 * 接收页面请求，根据页面的配置信息对请求进行分发
 */
@Controller
public class EServiceDispatcher {

    private Logger logger = LoggerFactory.getLogger(EServiceDispatcher.class);

//    @Autowired
//    @Qualifier("operateRecordLogMapper")
//    private OperateRecordLogMapper operateRecordLogMapper;

    @RequestMapping(value = "/dispatcher", method = RequestMethod.POST)
    @ResponseBody
    public Object dispatcher(String serviceParam, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String requestIdentifier = Thread.currentThread().getId() + "";
        RequestIdentifierLocalHolder.setRequestIdentifier(requestIdentifier);
        String serviceParamJsonFormat = JsonFormatUtils.formatJson(serviceParam);
        logger.info("【" + requestIdentifier + "】 : 传入参数是 : \r\n" + serviceParamJsonFormat);

        JSONObject rootObject = JSON.parseObject(serviceParam);
        // 获取服务名
        String serviceName = rootObject.getJSONObject("Header").getString("ServiceName");
        logger.info("【" + requestIdentifier + "】 : 调用服务是 : " + serviceName);
        String className = EsServiceCache.getClassName(serviceName);
        String methodName = EsServiceCache.getMethodName(serviceName);
        try {
            Class clazz = Class.forName(className);
            // 获取这个类的所有方法
            Method[] methods = clazz.getMethods();
            for(Method method : methods){
                // 获取这个方法对应的服务名
                String methodServiceName = this.getServiceNameByMethod(method);
                // 定位到符合要求的服务
                if(!"".equals(methodServiceName) && methodName.equals(method.getName()) && serviceName.equals(methodServiceName)){
                    // 记录操作日志
//                    OperateRecordLogPojo operateRecordLogPojo = new OperateRecordLogPojo();
//                    OperateRecordLogTextPojo operateRecordLogTextPojo = new OperateRecordLogTextPojo();
//                    UserPojo userPojo = HttpSessionTool.getValue(request,"user");
//                    operateRecordLogPojo.setUsername(userPojo==null?null:userPojo.getUsername());
//                    operateRecordLogPojo.setServiceName(serviceName);
//                    operateRecordLogPojo.setRequestIdentifier(requestIdentifier);
//                    operateRecordLogTextPojo.setParam(serviceParamJsonFormat);
//                    ManagerOperate managerOperate = method.getAnnotation(ManagerOperate.class);
//                    if(null != managerOperate){
//                        operateRecordLogPojo.setServiceDesc(managerOperate.value());
//                    }

                    // 获取此服务方法的参数
                    Object[] params = this.initMethodParam(method, serviceParam, request, response);
                    logger.info("【" + requestIdentifier + "】 : 参数组装成功!");
                    // 调用服务，获取服务返回值
//                    long startTime = System.currentTimeMillis();
                    Object result = method.invoke(clazz.newInstance(), params);
//                    long endTime = System.currentTimeMillis();
//                    if(null != result){
//                        operateRecordLogTextPojo.setReturnMsg(JSONObject.toJSONString(result));
//                        operateRecordLogPojo.setReturnCode(((RestResponse)result).getResponseHeader().getErrorCode());
//                        operateRecordLogPojo.setReturnMsg(((RestResponse)result).getResponseHeader().getMessage());
//                    }
//                    operateRecordLogPojo.setExecuteTime(endTime - startTime);
//                    operateRecordLogPojo.setOperateTime(new Date());
//                    this.operateRecordLogMapper.insertOperateRecord(operateRecordLogPojo);
//                    operateRecordLogTextPojo.setLogId(operateRecordLogPojo.getId());
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            EServiceDispatcher.this.operateRecordLogMapper.insertOperateRecordText(operateRecordLogTextPojo);
//                        }
//                    }).start();

                    logger.info("【" + requestIdentifier + "】 : 服务调用成功!");
                    return result;
                }else{
                    continue;
                }
            }
        } catch (Exception e){
            logger.error("Class: " + className + " not found", e);
            RestResponse restResponse = new RestResponse();
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("后台分发器异常");
            return restResponse;
        }
        return null;
    }

    /**
     * 初始化服务方法参数
     * @param method
     * @param serviceParam
     * @param request
     * @param response
     * @throws Exception
     */
    private Object[] initMethodParam(Method method, String serviceParam, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 方法参数类型列表
        Class[] paramClazz = method.getParameterTypes();
        Object[] params = new Object[paramClazz.length];
        for(int i = 0; i < paramClazz.length; i++){
            // 如果目标服务的参数是Request或者是Response，就直接赋值
            if(paramClazz[i] == HttpServletRequest.class){
                params[i] = request;
            }else if(paramClazz[i] == HttpServletResponse.class){
                params[i] = response;
            }
            // 否则构造业务参数对象并进行赋值
            else{
                Object obj = this.installParamValue(paramClazz[i], serviceParam);
                params[i] = obj;
            }
        }
        return params;
    }

    /**
     * 根据传入参数装载目标服务的业务参数
     */
    private Object installParamValue(Class paramClazz, String serviceParam) throws Exception{
        Object obj = null;
        try {
            obj = paramClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("实例化参数对象异常", e);
            throw e;
        } catch (IllegalAccessException e) {
            logger.error("实例化参数对象异常", e);
            throw e;
        }
        JSONObject rootObject = JSON.parseObject(serviceParam);
        JSONObject paramObject = rootObject.getJSONObject("Input");

        // 装载Field类型的普通属性
        JSONArray fieldsArray = paramObject.getJSONArray("Fields");
        if(null !=fieldsArray && !fieldsArray.isEmpty()){
            for(int i = 0; i < fieldsArray.size(); i++){
                // 获得一个键值对，如{"username" : "zhangsan"}
                JSONObject fieldJSONObject = fieldsArray.getJSONObject(i);
                this.setValueForField(fieldJSONObject, obj);
            }
        }
        // 装载Object类型的对象属性
        JSONArray objectsArray = paramObject.getJSONArray("Objects");
        if(null != objectsArray && !objectsArray.isEmpty()){
            for(int i = 0; i < objectsArray.size(); i++) {
                // 获得一个键值对，如 {user:{"username" : "zhangsan"}}
                JSONObject objectJSONObject = objectsArray.getJSONObject(i);
                this.setValueForObject(objectJSONObject, obj);
            }
        }
        // 装载arrays类型的对象属性
        JSONArray arraysArray = paramObject.getJSONArray("Arrays");
        if(null != arraysArray && !arraysArray.isEmpty()){
            for(int i = 0; i < arraysArray.size(); i++) {
                // 获得一个键值对，如  {"students":[{"name" : "maqi", "sex" : "男"}, {"name" : "maqi", "sex" : "女"}]}
                JSONObject objectJSONArray = arraysArray.getJSONObject(i);
                this.setValueForArray(objectJSONArray, obj);
            }
        }
//        logger.info(obj.toString());
        return obj;
    }

    /**
     * 为普通字段赋值
     * @param jsonObject　页面传来的Json对象
     * @param obj　为此对象对应的属性赋值
     */
    private void setValueForField(JSONObject jsonObject, Object obj) throws Exception{
        // 获得属性名和属性值
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for(Iterator<Map.Entry<String, Object>> ite = entries.iterator(); ite.hasNext();){
            Map.Entry<String, Object> entry = ite.next();
            // 获得属性名
            String key = entry.getKey();
            // 获得属性值
            Object value = entry.getValue();
            // 根据属性名获得此属性的set方法名
            // String setMethod = "set" + new String(new char[]{key.charAt(0)}).toUpperCase() + key.substring(1);
            Field field = null;
            try {
                field = obj.getClass().getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                logger.error("No field found : " + key, e);
                throw e;
            }
            field.setAccessible(true);
            try {
//                field.set(obj, value);
                this.setFieldValue(field, obj, value);
            } catch (IllegalAccessException e) {
                logger.info("Set field value error [key : " + key + ", value : " + value + "]", e);
                throw e;
            } catch (Exception e) {
                logger.error("Set field value occur error : " + field.getName(), e);
                throw e;
            }
        }
    }

    /**
     * 为对象（结构）类型的属性赋值 {user:{"username" : "zhangsan", "birthday" : "2014-5-9"}}
     * @param jsonObject
     * @param obj
     */
    private void setValueForObject(JSONObject jsonObject, Object obj) throws Exception{
        // 获得属性名和属性值
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for(Iterator<Map.Entry<String, Object>> ite = entries.iterator(); ite.hasNext();) {
            Map.Entry<String, Object> entry = ite.next();
            // 获得属性名
            String key = entry.getKey();
            // 调用get方法获取此属性
            String getMethod = "get" + new String(new char[]{key.charAt(0)}).toUpperCase() + key.substring(1);
            String setMethod = "set" + new String(new char[]{key.charAt(0)}).toUpperCase() + key.substring(1);

            Object propertyObj = this.getPropertyByGetMethod(getMethod, obj);
            // 为此对象赋值
            if(null == propertyObj){
                Class fieldClass = null;
                try {
                    Field field = obj.getClass().getDeclaredField(key);
                    fieldClass = field.getType();
                    // 如果是基础类型，就直接赋值
                    if(this.isBasicType(fieldClass)){
                        field.setAccessible(true);
//                        field.set(obj, entry.getValue());
                        this.setFieldValue(field, obj, entry.getValue());
                    }else{ //　否则就初始化实例，并进行赋值
                        Object fieldObj = null;
                        Class componentType = null;
                        if(fieldClass.isAssignableFrom(List.class) || fieldClass.isArray()){
                            componentType = this.getComponentType(field, fieldClass);
                            fieldObj = this.initListOrArray(fieldClass, field, setMethod, obj, jsonObject, key);
                        }else{ // 对象类型
                            fieldObj = fieldClass.newInstance();
                            obj.getClass().getDeclaredMethod(setMethod, fieldClass).invoke(obj, fieldObj);
                        }
                        Object objForKey = jsonObject.get(key);
                        // 判断属性对象的类型
                        if(objForKey instanceof JSONObject){ // 普通对象类型
                            this.setValueForObject((JSONObject)objForKey, fieldObj);
                        }else if(objForKey instanceof JSONArray){ // 数组类型
                            JSONArray array = (JSONArray)objForKey;
                            for(int i = 0; i < array.size(); i++){
                                Object objOfArray = array.get(i);
                                // 判断数组里面的每个元素的类型
                                if(objOfArray instanceof JSONObject){ // 普通对象类型
                                    Object componentObj = this.addElementForArrOrList(componentType, fieldClass, fieldObj, i);
                                    this.setValueForObject((JSONObject)objOfArray, componentObj);
                                }
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    logger.error("Field :" + key + " not exists!", e);
                    throw e;
                } catch (IllegalAccessException e) {
                    logger.error("通过反射获取方法 " + setMethod + "　发生异常", e);
                    throw e;
                } catch (InstantiationException e) {
                    logger.error("初始化 " + fieldClass.getName() + "　异常", e);
                    throw e;
                } catch (NoSuchMethodException e) {
                    logger.error("Method : " + setMethod + " not exists!", e);
                    throw e;
                } catch (InvocationTargetException e) {
                    logger.error("通过反射执行 " + setMethod + " 方法发生异常", e);
                    throw e;
                }
            }
        }
    }

    /**
     *
     * @param jsonObject {"students":[{"name" : "maqi", "sex" : "男"}, {"name" : "maqi", "sex" : "女"}]}
     * @param obj
     */
    private void setValueForArray(JSONObject jsonObject, Object obj) throws Exception{
        // 获得属性名和属性值
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for(Iterator<Map.Entry<String, Object>> ite = entries.iterator(); ite.hasNext();) {
            Map.Entry<String, Object> entry = ite.next();
            // 获得属性名
            String key = entry.getKey();
            // 调用get方法获取此属性
            String getMethod = "get" + new String(new char[]{key.charAt(0)}).toUpperCase() + key.substring(1);
            String setMethod = "set" + new String(new char[]{key.charAt(0)}).toUpperCase() + key.substring(1);
            Object propertyObj = this.getPropertyByGetMethod(getMethod, obj);
            // 初始化列表或者数组属性
            if(null == propertyObj){
                Class fieldClass = null;
                Class componentType = null;
                Object fieldObj = null;
                try {
                    Field field = obj.getClass().getDeclaredField(key);
                    fieldClass = field.getType();
                    componentType = this.getComponentType(field, fieldClass);
                    fieldObj = this.initListOrArray(fieldClass, field, setMethod, obj, jsonObject, key);
                    Object objForKey = jsonObject.get(key);
                    // 为这个列表或数组增加元素
                    JSONArray array = (JSONArray)objForKey;
                    for(int i = 0; i < array.size(); i++){
                        Object objOfArray = array.get(i);
                        // 判断数组里面的每个元素的类型
                        if(objOfArray instanceof JSONObject){ // 普通对象类型
                            Object componentObj = this.addElementForArrOrList(componentType, fieldClass, fieldObj, i);
                            this.setValueForObject((JSONObject)objOfArray, componentObj);
                        }
                        // 数组或者列表类型(此时相当于传入了一个二维数组)
                        // 实际业务中不存在这种情况，暂不实现
                        else if(objOfArray instanceof JSONArray){


                        }
                    }
                    //this.setValueForArray((JSONObject)objForKey, fieldObj);
                } catch (NoSuchFieldException e) {
                    logger.error("Field :" + key + " not exists!", e);
                    throw e;
                } catch (IllegalAccessException e) {
                    logger.error("通过反射获取方法 " + setMethod + "　发生异常", e);
                    throw e;
                } catch (InstantiationException e) {
                    logger.error("初始化 " + fieldClass.getName() + "　异常", e);
                    throw e;
                } catch (NoSuchMethodException e) {
                    logger.error("Method : " + setMethod + " not exists!", e);
                    throw e;
                } catch (InvocationTargetException e) {
                    logger.error("通过反射执行 " + setMethod + " 方法发生异常", e);
                    throw e;
                }
            }
        }
    }

    /**
     * 初始化集合或数组
     * @param fieldClass
     * @param field
     * @param setMethod
     * @param obj
     * @param jsonObject
     * @param key
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object initListOrArray(Class fieldClass, Field field, String setMethod, Object obj, JSONObject jsonObject, String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object fieldObj = null;
        if(fieldClass.isAssignableFrom(ArrayList.class)){ //如果是List类型的属性，优先使用ArrayList
            fieldObj = new ArrayList<>();
            obj.getClass().getDeclaredMethod(setMethod, fieldClass).invoke(obj, fieldObj);
        }else if(fieldClass.isAssignableFrom(LinkedList.class)){ // 如果明确指定LinkedList，就直接赋值为LinkedList
            fieldObj = new LinkedList<>();
            obj.getClass().getDeclaredMethod(setMethod, fieldClass).invoke(obj, fieldObj);
        }else if(fieldClass.isArray()){ // 数组类型
            // 获取这个数组的组件类型
            Class componentType = this.getComponentType(field, fieldClass);
            int arraySize = jsonObject.getJSONArray(key).size();
            fieldObj = Array.newInstance(componentType, arraySize);
            obj.getClass().getDeclaredMethod(setMethod, fieldClass).invoke(obj, fieldObj);
        }
        return fieldObj;
    }

    /**
     * 获取数组和集合的组件类型
     * @param field
     * @param fieldClass
     * @return
     */
    private Class getComponentType(Field field, Class fieldClass){
        Class componentType = null;
        if(fieldClass.isAssignableFrom(ArrayList.class)) { //如果是List类型的属性，优先使用ArrayList
            componentType = this.getGenericClazz(field);
        }else if(fieldClass.isAssignableFrom(LinkedList.class)) { // 如果明确指定LinkedList，就直接赋值为LinkedList
            componentType = this.getGenericClazz(field);
        }else if(fieldClass.isArray()) { // 数组类型
            // 获取这个数组的组件类型
            componentType = fieldClass.getComponentType();
        }
        return componentType;
    }

    /**
     * 为一个列表或一个数组添加元素
     * @param componentType
     * @param fieldClass
     * @param fieldObj
     * @param index
     * @return
     * @throws Exception
     */
    private Object addElementForArrOrList(Class componentType, Class fieldClass, Object fieldObj, int index) throws Exception {
        // 判断数组里面的每个元素的类型
        Object componentObj = componentType.newInstance();
        if(fieldClass.isAssignableFrom(List.class)){ // List类型
            Method addMethod = fieldClass.getMethod("add", Object.class);
            addMethod.invoke(fieldObj, componentObj);
        }else if(fieldClass.isArray()){ // 数组类型
            Array.set(fieldObj, index, componentObj);
        }
        return componentObj;
    }

    /**
     * 判断是否是基本数据类型
     * @param clazz
     * @return
     */
    private boolean isBasicType(Class clazz){
        return (clazz == Boolean.TYPE || clazz == Boolean.class) || (clazz == Byte.TYPE  || clazz == Byte.class)
                || (clazz == Short.TYPE  || clazz == Short.class) || (clazz == Integer.TYPE  || clazz == Integer.class)
                || (clazz == Long.TYPE  || clazz == Long.class) || (clazz == Float.TYPE  || clazz == Float.class)
                || (clazz == Double.TYPE  || clazz == Double.class) || (clazz == String.class)
                || (clazz == BigDecimal.class) || (clazz == BigInteger.class) || (clazz == Date.class)
                || (clazz == java.sql.Date.class) || (clazz == Timestamp.class) || (clazz.isEnum())
                || Calendar.class.isAssignableFrom(clazz);
    }

    /**
     * 设置基础字段值
     * @param field
     * @param obj
     * @param value
     * @throws Exception
     */
    private void setFieldValue(Field field, Object obj, Object value) throws Exception{
        Class fieldClass = field.getType();
        if(fieldClass == Boolean.TYPE || fieldClass == Boolean.class){
            if(value.getClass() == String.class){
                field.set(obj, Boolean.parseBoolean((String)value));
            }else if(value.getClass() == Boolean.TYPE || value.getClass() == Boolean.class){
                field.set(obj, value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == Byte.TYPE || fieldClass == Byte.class){

        }else if(fieldClass == Short.TYPE || fieldClass == Short.class){
            if(value.getClass() == String.class){
                field.set(obj, Short.parseShort((String)value));
            }else if(value.getClass() == Short.TYPE || value.getClass() == Short.class){
                field.set(obj, value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == Integer.TYPE || fieldClass == Integer.class){
            if(value.getClass() == String.class){
                field.set(obj, Integer.parseInt((String)value));
            }else if(value.getClass() == Integer.TYPE || value.getClass() == Integer.class){
                field.set(obj, value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == Long.TYPE || fieldClass == Long.class){
            if(value.getClass() == String.class){
                field.set(obj, Long.parseLong((String)value));
            }else if(value.getClass() == Long.TYPE || value.getClass() == Long.class|| value.getClass() == Integer.class){
                field.set(obj, Long.parseLong(String.valueOf(value)));
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == Float.TYPE || fieldClass == Float.class){
            if(value.getClass() == String.class){
                field.set(obj, Float.parseFloat((String)value));
            }else if(value.getClass() == Float.TYPE || value.getClass() == Float.class){
                field.set(obj, value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == Double.TYPE || fieldClass == Double.class){
            if(value.getClass() == String.class){
                field.set(obj, Double.parseDouble((String)value));
            }else if(value.getClass() == Double.TYPE || value.getClass() == Double.class){
                field.set(obj, value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == String.class){
            field.set(obj, value);
        }else if(fieldClass == BigDecimal.class){

        }else if(fieldClass == BigInteger.class){

        }else if(fieldClass == Date.class){

            if(value.getClass() == String.class){
                DateFormat format = null;

                if(((String)value).length() == 10){
                    format = new SimpleDateFormat("yyyy-MM-dd");
                }else if(((String)value).length() == 8){
                    format = new SimpleDateFormat("HH:mm:ss");
                }else{
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                field.set(obj, format.parse((String)value));
            }else if(value.getClass() == Date.class){
                field.set(obj,value);
            }else {
                throw new Exception(field.getName() + " : error field type");
            }
        }else if(fieldClass == java.sql.Date.class){

        }else if(fieldClass == Timestamp.class){

        }

    }



    /**
     * 获取List类型属性的泛型Class对象
     * 如 List<A> 会返回A.class
     * @param field
     * @return
     */
    private Class getGenericClazz(Field field){
        Type fc = field.getGenericType(); // 如果是List类型，得到其Generic的类型
        if(fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Class genericClazz = (Class) pt.getActualTypeArguments()[0]; //【4】 得到泛型里的class类型对象。
            return genericClazz;
        }
        return null;
    }

    /**
     * 通过调用get方法获取属性
     * @return
     */
    private Object getPropertyByGetMethod(String getMethod, Object obj) throws Exception{
        Object propertyObj = null;
        try {
            propertyObj = obj.getClass().getMethod(getMethod).invoke(obj, null);
        } catch (IllegalAccessException e) {
            logger.error("Access method : " + getMethod + " occurred error!", e);
            throw e;
        } catch (InvocationTargetException e) {
            logger.error("Invoke method : " + getMethod + " occurred error!", e);
            throw e;
        } catch (NoSuchMethodException e) {
            logger.error("Method " + getMethod + " does not exists!", e);
            throw e;
        }
        return propertyObj;
    }

    /**
     * 获取某个方法对应的服务名
     * @param method
     * @return
     */
    private String getServiceNameByMethod(Method method){
        EService eService = method.getAnnotation(EService.class);
        if(null == eService) return "";
        return eService.value();
    }

}
