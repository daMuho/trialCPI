import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.XmlUtil;
import groovy.xml.*;
def Message processData(Message message) {
  //Body
  def body = message.getBody(String);
  def finalbody = "";
  def xml = new XmlSlurper().parseText(body)
   def skip = ["root", "name", "elements", "end_of_month", "preliminary"]
    finalbody += "<Process><ProcessHeaderType><ExRate>"
    xml.'**'.each { line ->
        if (skip.contains(line.name())) {
        } else {
            finalbody += "<ExRateType>"
            finalbody += "<Currency>" + line.name() + "</Currency>"
            // original code - begin
            //def (v1, v2) = line.name().split("_") original code
            //finalbody += "<FromCurrency>" + v1 + "</FromCurrency>"
            //finalbody += "<ToCurrency>" + v2 + "</ToCurrency>"
            // original code - end            
            //begin - odeck
            def v = line.name().split("_");
            finalbody += "<FromCurrency>" + v[0] + "</FromCurrency>"
            finalbody += "<ToCurrency>" + v[1] + "</ToCurrency>"
            
            if (v.size() > 2) {
                finalbody += "<Factor>" + v[2] + "</Factor>" }
  
            else  {
                finalbody += "<Factor>1</Factor>"
            }
            //end - odeck
            finalbody += "<Rate>" + line.text() + "</Rate>"
            finalbody += "</ExRateType>"
        }
        
    }
    finalbody += "</ExRate></ProcessHeaderType></Process>"
  message.setBody(XmlUtil.serialize(finalbody));
  return message;
}