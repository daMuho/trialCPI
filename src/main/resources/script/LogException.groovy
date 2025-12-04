        import com.sap.gateway.ip.core.customdev.util.Message;

        def Message processData(Message message) {
                        
                        // get a map of iflow properties
                        def map = message.getProperties()
                        def logException = map.get("ENABLE_EXCEPTION_LOGGING")
                        def interfaceID = map.get("INTERFACE_ID").toString()
                        def attachID = ""
                        def errordetails = ""

                        // get an exception java class instance
                        def ex = map.get("CamelExceptionCaught")
                        if (ex!=null) 
                        {
                        // save the error response as a message attachment 
                        def messageLog = messageLogFactory.getMessageLog(message);
                        errordetails = "The " + interfaceID + " interface failed because of the following error:  " + ex.toString()
                        attachID  = "Error Details for " + interfaceID.toString()
                        
                        if (logException != null && logException.equalsIgnoreCase("TRUE")) 
                        {
                            messageLog.addAttachmentAsString(attachID, errordetails, "text/plain");
                        }
                        
                        }
                        
                        message.setProperty("ErrorText", errordetails);

                        return message;
        }