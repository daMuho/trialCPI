import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
    def properties = message.getProperties();
	String enableLogging = properties.get("ENABLE_PAYLOAD_LOGGING");
	 def INTERFACE_ID = properties.get("INTERFACE_ID");
	if(enableLogging != null && enableLogging.toUpperCase().equals("TRUE")){
		def body = message.getBody(java.lang.String) as String;
		def messageLog = messageLogFactory.getMessageLog(message);
		if (messageLog != null) {
		  messageLog.addAttachmentAsString(INTERFACE_ID, body, "text/xml");
		}
	}
       return message;
}

def Message ErrorLog(Message message) {
    //Body
    def body = message.getBody();
	def map = message.getProperties();
	
    //Headers
    def headers = message.getHeaders();
      SourceSystem = map.get("SourceSystem");
      TargetSystem = map.get("TargetSystem");
      InterfaceName = map.get("INTERFACE_NAME");
      InterfaceType = map.get("INTERFACE_TYPE");
      FileName = headers.get("CamelFileName");
	  API_UUID = headers.get("SAP_MplCorrelationId")
      SAPMessageID = headers.get("SAP_MessageProcessingLogID")
      loopCounter = message.getProperty("CamelLoopIndex");

    def dt =new Date().format("yyyyMMddHHmmss",TimeZone.getTimeZone('GMT+8'))
    def EntryID = InterfaceType + "_" + InterfaceName + "_" + dt
    message.setProperty("EntryID",EntryID );

    return message;
}