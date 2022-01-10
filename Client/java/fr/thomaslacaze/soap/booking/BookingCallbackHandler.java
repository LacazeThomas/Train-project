
/**
 * BookingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package fr.thomaslacaze.soap.booking;

    /**
     *  BookingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BookingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BookingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BookingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for reserve method
            * override this method for handling normal response from reserve operation
            */
           public void receiveResultreserve(
                    fr.thomaslacaze.soap.booking.BookingStub.ReserveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reserve operation
           */
            public void receiveErrorreserve(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ticketsByUser method
            * override this method for handling normal response from ticketsByUser operation
            */
           public void receiveResultticketsByUser(
                    fr.thomaslacaze.soap.booking.BookingStub.TicketsByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ticketsByUser operation
           */
            public void receiveErrorticketsByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ticketsByTrain method
            * override this method for handling normal response from ticketsByTrain operation
            */
           public void receiveResultticketsByTrain(
                    fr.thomaslacaze.soap.booking.BookingStub.TicketsByTrainResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ticketsByTrain operation
           */
            public void receiveErrorticketsByTrain(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for all method
            * override this method for handling normal response from all operation
            */
           public void receiveResultall(
                    fr.thomaslacaze.soap.booking.BookingStub.AllResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from all operation
           */
            public void receiveErrorall(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for trainById method
            * override this method for handling normal response from trainById operation
            */
           public void receiveResulttrainById(
                    fr.thomaslacaze.soap.booking.BookingStub.TrainByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from trainById operation
           */
            public void receiveErrortrainById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unReserve method
            * override this method for handling normal response from unReserve operation
            */
           public void receiveResultunReserve(
                    fr.thomaslacaze.soap.booking.BookingStub.UnReserveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unReserve operation
           */
            public void receiveErrorunReserve(java.lang.Exception e) {
            }
                


    }
    