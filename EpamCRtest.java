import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.ArrayList;

public class EpamCRtest { 
   public static void main(String[] args) {
	   String oneElement = "<a href=\"javascript:setLoading();self.location.href='page?D=KS&NodeNo=153&HtmFile=id_Default&OVERRIDE_KS_PAGES_INCLUDED_CHECK=1&ES=7329610234097029120&X=69145775&US=124374418015347691402521654501650&ES=7329610234097029120';\" id=\"textHrefElement1 137\" title=\"\">&nbsp;Workpapers</a>";
	   System.out.println("oneElement9 = " + oneElement);
	   
	  String res = EpamCRJmeterUtils.getInstanceLink(oneElement);
	  System.out.println("res = " + res);
   }
}

class EpamCRJmeterUtils {

    private EpamCRJmeterUtils() {}

    public static String getInstanceLink(String oneElement){
		
		List<Element> list = new ArrayList<Element>();
		Element newElement = new Element("a");
		
		newElement.append(oneElement);
		
		//System.out.println("list1 = " + list.get(0));
		System.out.println("newElement28 = " + newElement.toString());
		System.out.println("newElement.child()29 = " + newElement.child(0).toString());
		
		list.add(newElement.child(0));
		
		System.out.println("list.size32 = " + list.size());
		System.out.println("list33 = " + list.get(0));
		
		Elements element = new Elements(list);
		
        return getInstanceLink(element);
    }

    public static String getInstanceLink(Elements element){
        String instanceLink = parseLink(element);
		System.out.println("instanceLink = " + instanceLink);
        return instanceLink;
    }

    public static String parseLink(Elements element) {

        String link = "nf";
        if (!element.isEmpty()){
			String href = element.attr("href").split("'")[1];
            link = href;

        }
        return link;
    }

    public static String parseLink(Elements element, String parentWindowName, String jspid) {

        String link = "nf";
        if (!element.isEmpty()){
            String href = element.attr("href").split("'")[1];
            link = href;

        }
        return link;
    }

}