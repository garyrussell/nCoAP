/**
 * Copyright (c) 2012, Oliver Kleine, Institute of Telematics, University of Luebeck
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.uniluebeck.itm.spitfire.nCoap.application;

import de.uniluebeck.itm.spitfire.nCoap.core.CoapChannel;
import de.uniluebeck.itm.spitfire.nCoap.message.Message;
import de.uniluebeck.itm.spitfire.nCoap.message.Request;
import de.uniluebeck.itm.spitfire.nCoap.message.header.Code;
import de.uniluebeck.itm.spitfire.nCoap.message.header.MsgType;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannel;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;


/**
 * @author Oliver Kleine
 */
public class SimpleApplication implements ResponseCallback {

    private static Logger log = Logger.getLogger(SimpleApplication.class);

    private String name;
    private DatagramChannel channel;
    private long start;

    public SimpleApplication(String name){
        this.name = name;
        channel =  CoapChannel.getInstance().channel;
    }

    @Override
    public void responseReceived(Message message) {
        long end = System.currentTimeMillis();
        if(log.isDebugEnabled()){
            log.debug("[" + name + "] response received after " + (end - start) + " millis.");
            log.debug("[" + name + "] Payload: " + new String(message.getPayload().array(), Charset.forName("UTF-8")));
        }
    }

    public void writeRequest(MsgType msgType, Code code, URI targetURI) throws Exception{
        start = System.currentTimeMillis();
        Request request = new Request(msgType, code, targetURI, this);

        InetSocketAddress rcptSocketAddress = new InetSocketAddress(request.getTargetUri().getHost(),
                request.getTargetUri().getPort());

        Channels.write(channel, request, rcptSocketAddress);
    }

    /**
     * The main method to start the SimpleApplication
     * @param args args[0] must contain the target URI for the GET request
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        SimpleApplication app = new SimpleApplication("App");
        URI uri = new URI(args[0]);
        app.writeRequest(MsgType.CON, Code.GET, uri);
    }

//    private static class MessagePrinter extends Thread{
//        @Override
//        public void run(){
//            try {
//                sleep(60000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Sent Messages (" + OutgoingMessageReliabilityHandler.messagesSent.keySet().size() +
//                " IDs):");
//            for(Integer messageID : OutgoingMessageReliabilityHandler.messagesSent.keySet()){
//                System.out.println("Message ID: " + messageID);
//                Set<Date> dates = OutgoingMessageReliabilityHandler.messagesSent.get(messageID);
//                LinkedList<Date> dateslist = new LinkedList<>(dates);
//                Collections.sort(dateslist);
//                System.out.println(dateslist);
//            }
//        }
//    }
}
