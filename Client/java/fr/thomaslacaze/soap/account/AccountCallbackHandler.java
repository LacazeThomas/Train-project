
/**
 * AccountCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package fr.thomaslacaze.soap.account;

    /**
     *  AccountCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AccountCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AccountCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AccountCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for register method
            * override this method for handling normal response from register operation
            */
           public void receiveResultregister(
                    fr.thomaslacaze.soap.account.AccountStub.RegisterResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from register operation
           */
            public void receiveErrorregister(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getID method
            * override this method for handling normal response from getID operation
            */
           public void receiveResultgetID(
                    fr.thomaslacaze.soap.account.AccountStub.GetIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getID operation
           */
            public void receiveErrorgetID(java.lang.Exception e) {
            }
                


    }
    