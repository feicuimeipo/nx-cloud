package com.nx.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HttpClientJsonUtil {

	private static ObjectMapper jsonMapper = null;
	private static String JSON_OBJECT_PREFIX = "{";
	private static String JSON_ARRAY_PREFIX = "[";

	public static void setObjectMapper(ObjectMapper jsonMapper) {
		HttpClientJsonUtil.jsonMapper = jsonMapper;
	}

	public static ObjectMapper getMapper(){
		if(jsonMapper != null)return jsonMapper;
		synchronized (HttpClientJsonUtil.class) {
			if(jsonMapper != null)return jsonMapper;
			if(jsonMapper == null) {
				jsonMapper = new ObjectMapper();
				//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
				jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				jsonMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
				jsonMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
				jsonMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
				jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				jsonMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
				jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			}
		}
		return jsonMapper;
	}
	
	public static boolean isJsonString(String str) {
		return isJsonObjectString(str) || isJsonArrayString(str);
	}
	
    public static boolean isJsonObjectString(String str) {
		return StringUtils.trimToEmpty(str).startsWith(JSON_OBJECT_PREFIX);
	}
    
    public static boolean isJsonArrayString(String str) {
    	return StringUtils.trimToEmpty(str).startsWith(JSON_ARRAY_PREFIX);
	}
	
	public static String toJson(Object object) {
		try {
			return getMapper().writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	
	public static <T> T toObject(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return getMapper().readValue(jsonString, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	public static <T> List<T> toList(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		JavaType javaType = getMapper().getTypeFactory().constructParametricType(ArrayList.class, clazz);
		return toObject(jsonString, javaType);
	}


	public static <T> T toObject(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return getMapper().readValue(jsonString, javaType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static JsonNode selectJsonNode(String jsonString,String nodeName){
		JsonNode jsonNode = toJsonNode(jsonString);
		return nodeName == null ? jsonNode : jsonNode.get(nodeName);
	}
	
	public static JsonNode toJsonNode(String jsonString){
		try {
			JsonNode node = getMapper().readTree(jsonString);	
			return node;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * 
	 * @param jsonString
	 * @param attrs (e.g:info.user.id)
	 * @return
	 */
	public static String getJsonNodeValue(String jsonString, String attrs) {  
		if(StringUtils.isBlank(jsonString))return null;
		return getJsonNodeValue(selectJsonNode(jsonString, null), attrs);
	}
	

	/**
	 * 
	 * @param node
	 * @param attrs (e.g:info.user.id)
	 * @return
	 */
	public static String getJsonNodeValue(JsonNode node, String attrs) {  
        int index = attrs.indexOf(".");
        JsonNode subNode = null;
        if (index == -1) {  
            if (node != null) {  
            	if(node instanceof ArrayNode) {
                	ArrayNode arrayNode = (ArrayNode) node;
                	subNode = arrayNode.isEmpty() ? null : arrayNode.get(0).get(attrs);
                }else {
                	subNode = node.get(attrs);
                }
            	
            	if(subNode == null)return null;
            	if(subNode instanceof ValueNode) {
            		return subNode.asText();
            	}
            	
                return subNode.toString();
            }  
            return null;  
        } else {  
            String s1 = attrs.substring(0, index);  
            String s2 = attrs.substring(index + 1);  
            if(node instanceof ArrayNode) {
            	ArrayNode arrayNode = (ArrayNode) node;
            	subNode = arrayNode.isEmpty() ? null : arrayNode.get(0).get(s1);
            }else {
            	subNode = node.get(s1);
            }
			return getJsonNodeValue(subNode, s2);  
        }  
    }  
	

}