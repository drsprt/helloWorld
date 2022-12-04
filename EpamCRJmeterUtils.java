package com.epam.cr;

import org.apache.jmeter.threads.JMeterVariables;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.apache.logging.slf4j.Log4jLogger;
import java.util.List;
import java.util.ArrayList;

public class EpamCRJmeterUtils {

    private EpamCRJmeterUtils() {}

    public static String getInstanceLink(JMeterVariables vars, Elements element, String parentWindowName, String jspid,
                                         String prefix){
        return getInstanceLink(vars, element, parentWindowName, jspid, prefix, null, false);
    }

    public static String getInstanceLink(JMeterVariables vars, Elements element, String parentWindowName, String jspid,
                                         String prefix, Log4jLogger log){
        return getInstanceLink(vars, element, parentWindowName, jspid, prefix, log, true);
    }

    public static String getInstanceLink(JMeterVariables vars, String oneElement, String parentWindowName, String jspid,
                                         String prefix, Log4jLogger log, boolean enableLogging){
		Element newElement = new Element("a");
		newElement.append(oneElement);
		
		List<Element> list = new ArrayList<Element>();
		list.add(newElement.child(0));
		
		Elements element = new Elements(list);
		
		return getInstanceLink(vars, element, parentWindowName, jspid, prefix, log, enableLogging);
    }

    public static String getInstanceLink(JMeterVariables vars, Elements element, String parentWindowName, String jspid,
                                         String prefix, Log4jLogger log, boolean enableLogging){

        String instanceLink = parseLink(element, parentWindowName, jspid);
        String variableName = varsPut(vars, prefix, "Link", instanceLink);

        boolean logEnabled = false;
        if(enableLogging && log != null) {
            logEnabled = true;
        }

        if(logEnabled) {
            logInfo(variableName, instanceLink, log);
        }

        return instanceLink;
    }

    public static void getInstanceParams(JMeterVariables vars, String sUrl, List<String> paramName, String prefix){
        getInstanceParams(vars, sUrl, paramName, prefix, null, false);
    }

    public static void getInstanceParams(JMeterVariables vars, String sUrl, List<String> paramName, String prefix, Log4jLogger log){
        getInstanceParams(vars, sUrl, paramName, prefix, log, true);
    }

    public static void getInstanceParams(JMeterVariables vars, String sUrl, List<String> paramName, String prefix, Log4jLogger log, boolean enableLogging){

        boolean logEnabled = false;
        if(enableLogging && log != null) {
            logEnabled = true;
        }

        int iStart = sUrl.indexOf('?');
        if (iStart >= 0 && iStart < sUrl.length()) {
            String params = sUrl.substring(iStart + 1);
            String[] pairs = params.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=");
                String key = kv[0];
                if (key.isEmpty()) {
                    continue;
                }
                for (int i = 0; i < paramName.size(); i++) {
                    if (key.toUpperCase().equals(paramName.get(i).toUpperCase())) {
                        String paramValue = (kv.length > 1 ? kv[1] : "");
                         String variableName = varsPut(vars, prefix, paramName.get(i), paramValue);
                        if(logEnabled) {
                            logInfo(variableName, paramValue, log);
                        }
                    }
                }
            }
        }
}

    public static String parseLink(Elements element, String parentWindowName, String jspid) {

        String link = "nf";
        if (!element.isEmpty()){
            String href = element.attr("href").split("'")[1];
            link = href + "&ParentWindowName=" + parentWindowName + "&jspid=" + jspid;

        }
        return link;
    }


    public static String varsPut(JMeterVariables vars, String prefix, String paramName, String paramValue) {

        String variableName = prefix + paramName;
        vars.put(variableName, paramValue);
        return variableName;
    }

    public static void logInfo(String variableName, String variableValue, Log4jLogger log){
        log.info(String.format(
                "Adding key value pair as a variable: %s, %s", variableName, variableValue));
    }

}


