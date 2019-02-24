package mx.com.grupogigante.gestionvivienda.seguridad;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class SqlInjectionAndXSSRequestWrapper extends HttpServletRequestWrapper {

	private static Logger logger = LogManager.getLogger(SqlInjectionAndXSSRequestWrapper.class);

	public static  Map<String,String>  excludeFieldsMap = new HashMap<String,String>();

	public SqlInjectionAndXSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = replaceXSSAndSqlInjection(values[i],parameter);
		}
		return encodedValues;

		/*
  for (int i = 0; i < count; i++) {
   checkXSSAndSqlInjectionPresence(parameter,values[i]);
  }
  return values;
		 */
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return replaceXSSAndSqlInjection(value,parameter);
		//checkXSSAndSqlInjectionPresence(parameter,value);
		//return value;
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null){
			return null;
		}
		return replaceXSSAndSqlInjection(value,name);
	}

	public static String replaceXSSAndSqlInjection(String value, String fieldName) {
		//Eg:jsf_tree_64=jsf_tree_64  - See sql-xss-exclusion-filter.properties
		String fieldToExclude = excludeFieldsMap.get(fieldName);
		if((fieldToExclude != null) &&(fieldToExclude.equalsIgnoreCase(fieldName))){
			logger.debug("The field name:"+ fieldName +" should not be inspected by SqlInjectionAndXSSFilter.");
			return value;
		}
		String orgValue = new String(value);
		//value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
		value = value.replaceAll("script","");
		value = value.replaceAll("/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;))/i","");
		value = value.replaceAll("/((\\%27)|(\'))union/ix","");
		value = value.replaceAll("/\\w*((\\%27)|(\\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix",""); 
		value = value.replaceAll("insert|update|delete|having|drop|(\'|%27).(and|or).(\'|%27)|(\'|%27).%7C{0,2}|%7C{2}","");  
		value = value.replaceAll("/((\\%3C)|<)((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\n]+((\\%3E)|>)/I","");

		if(logger.isDebugEnabled()){
			if((orgValue != null) &&(value!= null) &&(!orgValue.equalsIgnoreCase(value))){
				logger.debug(" Value was changed by Filter from :"+ orgValue);
				logger.debug("          TO:"+ value);
			}
		}
		return value;
	}
}