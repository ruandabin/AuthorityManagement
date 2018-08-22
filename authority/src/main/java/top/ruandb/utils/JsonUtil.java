package top.ruandb.utils;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

@Slf4j
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	//objectMapper属性配置
	static{
		objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }
	/**
	 * obj to json string
	 * @param t obj
	 * @return json string
	 */
	public static <T> String obj2Sting(T t){
		if(t == null){
			return null ;
		}
		try {
			return t instanceof String ? (String)t : objectMapper.writeValueAsString(t);
		} catch (IOException e) {
			log.warn("parse object to String exception, error:{}",e);
			return null ;
		}
	}
	
	/**
	 * String to T
	 * @param str string
	 * @param typeReference T type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>  T string2Obj(String str,TypeReference<T> typeReference){
		if(str == null || typeReference == null){
			return null ;
		}
		try {
			return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str, typeReference));
		} catch (IOException e) {
			log.warn("parse String to Object exception, error:{}",e);
			return null ;
		}
	}
	
}
